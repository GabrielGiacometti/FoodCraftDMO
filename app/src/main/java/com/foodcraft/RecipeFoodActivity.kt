package com.foodcraft

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton

class RecipeFoodActivity : AppCompatActivity() {

    private lateinit var btnBack: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_food)

        setBtnBack()
        setBtnNewUser()
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

     private fun setBtnNewUser() {
        val btnNewUser = findViewById<TextView>(R.id.textView3)
         btnNewUser.setOnClickListener {
             val intent = Intent(
                 this@RecipeFoodActivity,
                 RegisterActivity::class.java
             )
             startActivity(intent)
         }
    }
}