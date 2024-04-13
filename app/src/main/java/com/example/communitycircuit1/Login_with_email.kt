package com.example.communitycircuit1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.communitycircuit1.databinding.ActivityLoginWithEmailBinding
import com.google.firebase.auth.FirebaseAuth

class Login_with_email : AppCompatActivity() {
    private lateinit var binding: ActivityLoginWithEmailBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginWithEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            val etLogin = binding.etEmail.text.toString()
            val etPassword = binding.etPassword.text.toString()

            auth.signInWithEmailAndPassword(etLogin, etPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val i = Intent(this@Login_with_email, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val i = Intent(this@Login_with_email, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
