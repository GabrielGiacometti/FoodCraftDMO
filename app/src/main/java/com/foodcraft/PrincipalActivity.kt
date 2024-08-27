package com.foodcraft

import android.os.Bundle
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.foodcraft.adapter.RecipeAdapter
import com.foodcraft.repository.RecipeRepository
import android.util.Log
import android.widget.EditText
import android.text.Editable
import androidx.recyclerview.widget.LinearLayoutManager


class PrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var editTextFilter: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        editTextFilter = findViewById(R.id.editTextIngredients)
        recyclerView = findViewById(R.id.recyclerViewRecipes)
        recipeAdapter = RecipeAdapter(emptyList())

        val recipeRepository = RecipeRepository()
        recipeRepository.getRecipes(
            onSuccess = { recipes ->
                recipeAdapter = RecipeAdapter(recipes)
                recyclerView.adapter = recipeAdapter
            },
            onError = { error ->
                Log.e("RecipeRepository", "Error getting recipes:", error.toException())
            }
        )

        recyclerView.adapter = recipeAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        editTextFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filterText = s.toString().trim()
                recipeAdapter.filter(filterText)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

}