package com.foodcraft.model

data class RecipeModel(
    val name: String,
    val imageUrl : String,
    val description: String,
    val recipeIngredient: List<String>,
    val recipeInstructions: List<String>,
    val totalTime: Long? = null,
    val recipeYield: Int,
    val recipeCategory: List<String>,
    val recipeCuisine: List<String>,

) {
    constructor() : this(
        name = "",
        imageUrl  = "",
        description = "",
        recipeIngredient = emptyList(),
        recipeInstructions = emptyList(),
        recipeYield = 0,
        totalTime = null,
        recipeCategory = emptyList(),
        recipeCuisine = emptyList()
    )
}