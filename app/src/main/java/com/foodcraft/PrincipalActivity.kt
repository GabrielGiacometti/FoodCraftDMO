package com.foodcraft

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.foodcraft.adapter.RecipeAdapter
import com.foodcraft.repository.RecipeRepository
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager


class PrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

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
    }
}