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

    lateinit var btnLoginUser: Button
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setBtnNewUser()

    }

    private fun setBtnNewUser() {
        val btnNewUser = findViewById<TextView>(R.id.button3)
        btnNewUser.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@ProfileActivity,
                RecipeFoodActivity::class.java
            )
            startActivity(intent)

        })
    }

}