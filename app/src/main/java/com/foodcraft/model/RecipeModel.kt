package com.foodcraft.model

data class RecipeModel(
    val name: String,
    val image: String,
    val description: String,
    val recipeIngredient: List<String>,
    val recipeInstructions: List<String>,
    val totalTime: String,
//    val recipeYield: Int,
    val recipeCategory: List<String>,
    val recipeCuisine: List<String>,
)