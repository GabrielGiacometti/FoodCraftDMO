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


class LoginActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var txtTitle: TextView
    lateinit var btnNewUser: Button
    lateinit var btnLoginUser: Button
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setBtnNewUser()
        setBtnLoginUser()
    }

    private fun setBtnLoginUser() {
        edtEmail = findViewById(R.id.editTextTextEmailAddress)
        edtPassword = findViewById(R.id.editTextTextPassword2)
        btnLoginUser = findViewById(R.id.button2)
        btnLoginUser.setOnClickListener {
            userViewModel.login(edtEmail.text.toString(), edtPassword.text.toString()).observe(this, Observer {
                if(it == null)
                    Toast.makeText(applicationContext, getString(R.string.login_message), Toast.LENGTH_SHORT).show()
                else {
                    intent = Intent(
                        this@LoginActivity,
                        RegisterActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            })
        }
    }

    private fun setBtnNewUser() {
        val btnNewUser = findViewById<TextView>(R.id.textView3)
        btnNewUser.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@LoginActivity,
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