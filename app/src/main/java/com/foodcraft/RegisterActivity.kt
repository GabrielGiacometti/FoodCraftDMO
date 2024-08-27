package com.foodcraft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.foodcraft.model.User
import com.foodcraft.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    lateinit var edtName: TextInputEditText
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var edtConfirmPassword: EditText
    lateinit var btnUserRegister: Button

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setBtnUserRegister()
    }

    private fun setBtnUserRegister() {
        edtEmail = findViewById(R.id.editTextTextEmailAddress2)
        edtPassword = findViewById(R.id.editTextTextPassword)
        edtConfirmPassword = findViewById(R.id.editTextTextConfirmPassword)
        btnUserRegister = findViewById(R.id.button3)
        btnUserRegister.setOnClickListener {
            if (validate()) {
                val user = User(
                    email = edtEmail.text.toString(),
                    password = edtPassword.text.toString()
                )

                userViewModel.createUser(user)
                userViewModel.login(user.email, user.password).observe(this, Observer {
                    finish()
                })

            }
        }

    }


    private fun validate(): Boolean {
        val password = edtPassword.text.toString()
        val confirmPassword = edtConfirmPassword.text.toString()
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
        edtConfirmPassword.apply {
            if (text.isNullOrEmpty()) {
                error = "Preencha o campo confirmar senha."
                isValid = false
            }else if (confirmPassword != password) {
                error = "A confirmação da senha não confere."
                isValid = false
            }
            else {
                error = null
            }
        }

        return isValid
    }



}