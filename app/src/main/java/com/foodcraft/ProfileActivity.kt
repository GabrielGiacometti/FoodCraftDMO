package com.foodcraft

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.foodcraft.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText


class ProfileActivity : AppCompatActivity() {

    lateinit var btnFavorites: Button
    lateinit var btnSaveChanges: Button
    lateinit var btnBack: Button

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setBtnSaveChanges()
        setBtnFavorites()
        setBtnBack()
    }

    private fun setBtnSaveChanges() {
        btnSaveChanges = findViewById(R.id.button3)
        btnSaveChanges.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@ProfileActivity,
                PrincipalActivity::class.java
            )
            startActivity(intent)

        })
    }

    private fun setBtnFavorites() {
        btnFavorites = findViewById(R.id.favoriteButton)
        btnFavorites.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@ProfileActivity,
                FavoriteActivity::class.java
            )
            startActivity(intent)

        })
    }

    private fun setBtnBack() {
        btnBack = findViewById(R.id.backButton)
        btnBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@ProfileActivity,
                PrincipalActivity::class.java
            )
            startActivity(intent)

        })
    }
}