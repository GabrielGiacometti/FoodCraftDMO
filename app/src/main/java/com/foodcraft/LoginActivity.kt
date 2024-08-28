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
import androidx.lifecycle.Observer
import com.foodcraft.repository.CurrentUserSingleton
import com.foodcraft.viewmodel.UserViewModel


class LoginActivity : AppCompatActivity() {

    lateinit var btnLoginUser: Button
    lateinit var edtRecovery: TextView
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setBtnRecovery()
        setBtnNewUser()
        setBtnLoginUser()
    }

    private fun setBtnLoginUser() {
        edtEmail = findViewById(R.id.editTextTextEmailAddress)
        edtPassword = findViewById(R.id.editTextTextPassword2)
        btnLoginUser = findViewById(R.id.button2)
        btnLoginUser.setOnClickListener {

            intent = Intent(
                this@LoginActivity,
                PrincipalActivity::class.java
            )
            startActivity(intent)
            finish()

//            if (validate()) {
//                userViewModel.login(edtEmail.text.toString(), edtPassword.text.toString())
//                    .observe(this, Observer { user ->
//                        if (user == null) {
//                            Toast.makeText(
//                                applicationContext,
//                                getString(R.string.login_message),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            return@Observer
//                        } else {
//                            CurrentUserSingleton.setUser(user)
//                            intent = Intent(
//                                this@LoginActivity,
//                                PrincipalActivity::class.java
//                            )
//                            startActivity(intent)
//                            finish()
//                        }
//                    })
//            }
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

    private fun setBtnRecovery() {
        edtRecovery = findViewById<TextView>(R.id.textViewRecovery)
        edtRecovery.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@LoginActivity,
                RecoveryActivity::class.java
            )
            startActivity(intent)
        })
    }

    private fun validate(): Boolean {
        var isValid = true

        edtEmail.apply {
            if (text.isNullOrEmpty()) {
                error = "Preencha o campo email."
                isValid = false
            } else {
                error = null
            }
        }
        edtPassword.apply {
            if (text.isNullOrEmpty()) {
                error = "Preencha o campo senha."
                isValid = false
            } else {
                error = null
            }
        }
        return isValid
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}