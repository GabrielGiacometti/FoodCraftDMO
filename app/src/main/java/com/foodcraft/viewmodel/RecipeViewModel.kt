package com.foodcraft.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodcraft.model.RecipeModel
import com.foodcraft.repository.RecipeRepository
import android.util.Log

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val recipes = MutableLiveData<List<RecipeModel>>()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        repository.getRecipes(
            onSuccess = { recipes ->
                this.recipes.value = recipes
            },
            onError = { error ->
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        )
    }
}