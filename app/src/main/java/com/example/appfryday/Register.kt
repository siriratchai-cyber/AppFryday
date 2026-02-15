package com.example.appfryday

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val email = findViewById<TextInputEditText>(R.id.etRegEmail)
        val password = findViewById<TextInputEditText>(R.id.etRegPassword)
        val createBtn = findViewById<MaterialButton>(R.id.btnCreateAccount)

        createBtn.setOnClickListener {

            val e = email.text.toString().trim()
            val p = password.text.toString().trim()

            if (e.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (p.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener {

                    if (it.isSuccessful) {

                        val userId = auth.currentUser?.uid

                        val user = hashMapOf(
                            "email" to e,
                            "role" to "customer"
                        )

                        userId?.let { id ->
                            db.collection("users")
                                .document(id)
                                .set(user)
                        }

                        Toast.makeText(this, "Account Created 🎉", Toast.LENGTH_SHORT).show()
                        finish()

                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
