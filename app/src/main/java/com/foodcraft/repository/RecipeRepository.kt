package com.foodcraft.repository

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.foodcraft.model.RecipeModel
import com.google.firebase.database.DataSnapshot

class RecipeRepository {
    private val database = FirebaseDatabase.getInstance()
    private val recipesReference = database.getReference("recipes")

    fun getRecipes(onSuccess: (List<RecipeModel>) -> Unit, onError: (DatabaseError) -> Unit) {
        recipesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val recipeList = mutableListOf<RecipeModel>()
                for (snapshot in dataSnapshot.children) {
                    val recipe = snapshot.getValue(RecipeModel::class.java)
                    recipe?.let { recipeList.add(it) }
                }
                onSuccess(recipeList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }
}