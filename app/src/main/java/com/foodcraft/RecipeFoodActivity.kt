package com.foodcraft

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.bumptech.glide.Glide

class RecipeFoodActivity : AppCompatActivity() {

    private lateinit var btnBack: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_food)
        setBtnBack()
        val recipeName = intent.getStringExtra("RECIPE_NAME")
        val recipeImage = intent.getStringExtra("RECIPE_IMAGE")?.trim()
        val recipeDescription = intent.getStringExtra("RECIPE_DESCRIPTION")
        val recipeIngredient = intent.getStringExtra("RECIPE_INGREDIENTS")
        val recipeInstructions = intent.getStringExtra("RECIPE_INSTRUCTIONS")

        findViewById<TextView>(R.id.foodTitleText).text = recipeName
        Glide.with(this)
            .load(recipeImage)
            .into(findViewById(R.id.foodImage))
        findViewById<TextView>(R.id.descriptionFoodText).text = recipeDescription
        findViewById<TextView>(R.id.ingredientsDescription).text = recipeIngredient
        findViewById<TextView>(R.id.prepareDescription).text = recipeInstructions
    }



     private fun setBtnBack() {
         btnBack = findViewById(R.id.backButton)
         btnBack.setOnClickListener {
             val intent = Intent(
                 this@RecipeFoodActivity,
                 PrincipalActivity::class.java
             )
             startActivity(intent)
         }
     }


}