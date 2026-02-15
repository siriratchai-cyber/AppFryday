package com.example.appfryday

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<TextInputEditText>(R.id.etForgotEmail)
        val resetBtn = findViewById<MaterialButton>(R.id.btnResetPassword)

        resetBtn.setOnClickListener {

            val e = email.text.toString().trim()

            if (e.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(e)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        Toast.makeText(this, "Reset email sent 📩", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
