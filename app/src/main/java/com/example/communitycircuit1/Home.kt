package com.example.communitycircuit1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.communitycircuit1.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.btnlogin.setOnClickListener{
            // Check if the user is already signed in
            if (auth.currentUser == null) {
                startActivity(Intent(this, LoginPage::class.java))
            } else {
                // User is already signed in, redirect to another activity (e.g., MainActivity2)
                startActivity(Intent(this, MainActivity2::class.java))
                finish() // Optional: finish the current activity to prevent the user from going back
            }
        }
        binding.btnsignup.setOnClickListener {
            startActivity(Intent(this,SignupPage::class.java))
        }
    }
}
