package com.foodcraft

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodcraft.adapter.RecipeAdapter
import com.foodcraft.database.IngredientList
import com.foodcraft.model.RecipeModel
import com.foodcraft.repository.RecipeRepository

class PrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var editTextFilter: EditText
    private lateinit var buttonAddIngredients: ImageButton
    private lateinit var buttonProfile: ImageView
    private val recipeRepository = RecipeRepository() // Criação única de RecipeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        initializeViews()
        setupRecyclerView()
        setupEditTextFilter()
        setupButtonAddIngredients()
    }

    private fun setBtnProfile() {
        buttonProfile = findViewById(R.id.avatarImage)
        buttonProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@PrincipalActivity,
                ProfileActivity::class.java
            )
            startActivity(intent)
        })
    }

    private fun initializeViews() {
        editTextFilter = findViewById(R.id.editTextIngredients)
        recyclerView = findViewById(R.id.recyclerViewRecipes)
        buttonAddIngredients = findViewById(R.id.buttonAddIngredients)
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(emptyList()) { selectedRecipe ->
            navigateToRecipeDetails(selectedRecipe)
        }
        recyclerView.adapter = recipeAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recipeRepository.getRecipes(
            onSuccess = { recipes -> recipeAdapter.updateRecipes(recipes) },
            onError = { error -> Log.e("RecipeRepository", "Error getting recipes:", error.toException()) }
        )
    }

    private fun setupEditTextFilter() {
        editTextFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterRecipes(s.toString().trim())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupButtonAddIngredients() {
        buttonAddIngredients.setOnClickListener {
            showIngredientDialog(editTextFilter)
        }
    }

    private fun navigateToRecipeDetails(recipe: RecipeModel) {
        val intent = Intent(this, RecipeFoodActivity::class.java).apply {
            putExtra("RECIPE_NAME", recipe.name)
            putExtra("RECIPE_IMAGE", recipe.imageUrl)
            putExtra("RECIPE_DESCRIPTION", recipe.description)
            putExtra("RECIPE_INGREDIENTS", recipe.recipeIngredient.toTypedArray())
            putExtra("RECIPE_INSTRUCTIONS", recipe.recipeInstructions.toTypedArray())
            putExtra("TOTAL_TIME", recipe.totalTime)
            putExtra("RECIPE_YIELD", recipe.recipeYield)
            putExtra("RECIPE_CATEGORY", recipe.recipeCategory.toTypedArray())
            putExtra("RECIPE_CUISINE", recipe.recipeCuisine.toTypedArray())
        }
        startActivity(intent)
    }

    private fun filterRecipes(filterText: String) {
        val queries = filterText.split(",").map { it.trim().lowercase() }.filter { it.isNotEmpty() }

        recipeRepository.getRecipes(
            onSuccess = { recipes ->
                val filteredRecipes = if (filterText.isEmpty()) {
                    recipes
                } else {
                    recipes.filter { recipe ->
                        queries.any { query ->
                            recipe.recipeIngredient.any { ingredient ->
                                ingredient.lowercase().contains(query)
                            }
                        }
                    }
                }
                recipeAdapter.updateRecipes(filteredRecipes)
            },
            onError = { error -> Log.e("RecipeRepository", "Error getting recipes:", error.toException()) }
        )
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

        AlertDialog.Builder(this)
            .setTitle(R.string.escolha_ingredientes)
            .setView(dialogView)
            .setPositiveButton(R.string.add) { _, _ ->
                val selectedText = selectedIngredients.joinToString(", ")
                val currentText = editText.text.toString()
                editText.setText(if (currentText.isNotEmpty()) "$currentText, $selectedText" else selectedText)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
