package com.foodcraft

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RecoveryActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var resetPasswordButton: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)
        emailEditText = findViewById(R.id.editTextEmailAddress)
        resetPasswordButton = findViewById(R.id.buttonRecovery)
        setBtnBack()

        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isNotEmpty()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "E-mail de redefinição enviado!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Erro ao enviar e-mail: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                emailEditText.error = "Por favor, insira um e-mail válido"
            }
        }

    }

    private fun setBtnBack() {
        btnBack = findViewById(R.id.backButton)
        btnBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@RecoveryActivity,
                LoginActivity::class.java
            )
            startActivity(intent)

        })
    }
}