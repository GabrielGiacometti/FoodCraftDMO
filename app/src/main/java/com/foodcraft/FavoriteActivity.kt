package com.foodcraft

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FavoriteActivity : AppCompatActivity() {

    lateinit var buttonBack: Button
    lateinit var buttonProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setBtnBack()
        setupButtonProfile()
    }

    private fun setBtnBack() {
        buttonBack = findViewById(R.id.backButton)
        buttonBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@FavoriteActivity,
                PrincipalActivity::class.java
            )
            startActivity(intent)

        })
    }

    private fun setupButtonProfile() {
        buttonProfile = findViewById(R.id.avatarImage)
        buttonProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@FavoriteActivity,
                ProfileActivity::class.java
            )
            startActivity(intent)
        })
    }
}