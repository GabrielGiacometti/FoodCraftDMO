package com.foodcraft

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.foodcraft.viewmodel.UserViewModel

class RecipeFoodActivity : AppCompatActivity() {

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_food)


    }


    private fun setBtnNewUser() {
        val btnNewUser = findViewById<TextView>(R.id.textView3)
        btnNewUser.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@RecipeFoodActivity,
                RegisterActivity::class.java
            )
            startActivity(intent)

        })
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}