package com.foodcraft

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.foodcraft.adapter.RecipeAdapter
import com.foodcraft.repository.RecipeRepository
import android.util.Log
import android.widget.EditText
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodcraft.database.IngredientList


class PrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var editTextFilter: EditText
    private lateinit var buttonAddIngredients: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        initializeViews()
        setupRecyclerView()
        setupEditTextFilter()
        setupButtonAddIngredients()
    }

    private fun initializeViews() {
        editTextFilter = findViewById(R.id.editTextIngredients)
        recyclerView = findViewById(R.id.recyclerViewRecipes)
        buttonAddIngredients = findViewById(R.id.buttonAddIngredients)
    }
    private fun setupRecyclerView() {

        recipeAdapter = RecipeAdapter(emptyList()) { selectedRecipe ->
            val intent = Intent(this, RecipeFoodActivity::class.java).apply {
                putExtra("RECIPE_NAME", selectedRecipe.name)
                putExtra("RECIPE_IMAGE", selectedRecipe.imageUrl)
                putExtra("RECIPE_DESCRIPTION", selectedRecipe.description)
                putExtra("RECIPE_INGREDIENTS", selectedRecipe.recipeIngredient.toTypedArray())
                putExtra("RECIPE_INSTRUCTIONS", selectedRecipe.recipeInstructions.toTypedArray())
                putExtra("TOTAL_TIME", selectedRecipe.totalTime)
                putExtra("RECIPE_YIELD", selectedRecipe.recipeYield)
                putExtra("RECIPE_CATEGORY", selectedRecipe.recipeCategory.toTypedArray())
                putExtra("RECIPE_CUISINE", selectedRecipe.recipeCuisine.toTypedArray())
            }
            startActivity(intent)
        }

        recyclerView.adapter = recipeAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        val recipeRepository = RecipeRepository()
        recipeRepository.getRecipes(
            onSuccess = { recipes ->
                recipeAdapter.updateRecipes(recipes)
            },
            onError = { error ->
                Log.e("RecipeRepository", "Error getting recipes:", error.toException())
            }
        )
    }


    private fun setupEditTextFilter() {
        editTextFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filterText = s.toString().trim().lowercase()
                recipeAdapter.filter(filterText)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupButtonAddIngredients() {
        buttonAddIngredients.setOnClickListener {
            showIngredientDialog(editTextFilter)
        }
    }

    private fun showIngredientDialog(editText: EditText) {
        val ingredients = IngredientList.ingredients
        val selectedIngredients = mutableListOf<String>()

        val dialogView = layoutInflater.inflate(R.layout.dialog_ingredient_selector, null)
        val searchView = dialogView.findViewById<SearchView>(R.id.searchView)
        val listView = dialogView.findViewById<ListView>(R.id.listViewIngredients)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, ingredients)
        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = ingredients[position]
            if (listView.isItemChecked(position)) {
                selectedIngredients.add(selectedItem)
            } else {
                selectedIngredients.remove(selectedItem)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.escolha_ingredientes)
        builder.setView(dialogView)
        builder.setPositiveButton(R.string.add) { _, _ ->
            val selectedText = selectedIngredients.joinToString(", ")
            val currentText = editText.text.toString()
            editText.setText(if (currentText.isNotEmpty()) "$currentText, $selectedText" else selectedText)
        }
        builder.setNegativeButton(R.string.cancel, null)
        builder.show()
    }
}


