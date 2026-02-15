package com.example.appfryday

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val email = findViewById<TextInputEditText>(R.id.etEmail)
        val password = findViewById<TextInputEditText>(R.id.etPassword)
        val loginBtn = findViewById<MaterialButton>(R.id.btnLogin)
        val registerText = findViewById<TextView>(R.id.btnRegister)
        val forgotText = findViewById<TextView>(R.id.btnForgotPassword)

        // 🔐 LOGIN
        loginBtn.setOnClickListener {

            val e = email.text.toString().trim()
            val p = password.text.toString().trim()

            if (e.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val userId = auth.currentUser?.uid

                        userId?.let { id ->
                            db.collection("User")

                                .document(id)
                                .get()
                                .addOnSuccessListener { document ->

                                    val role = document.getString("role")

                                    when (role) {
                                        "shop" -> {
                                            Toast.makeText(this, "Welcome Shop 🏪", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(this, ShopHome::class.java))
                                            finish()
                                        }

                                        "customer" -> {
                                            Toast.makeText(this, "Welcome Customer 🍔", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(this, SearchMenu::class.java))
                                            finish()
                                        }

                                        else -> {
                                            Toast.makeText(this, "Role not found", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                        }

                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // 📝 ไปหน้า Register
        registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // 📩 ไปหน้า Forgot Password
        forgotText.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}
