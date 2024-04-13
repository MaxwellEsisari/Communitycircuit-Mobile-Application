package com.example.communitycircuit1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.communitycircuit1.databinding.ActivitySettingsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class Settings : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.signOutBtn.setOnClickListener {
            signOutAndNavigateToHome()
        }
    }

    private fun signOutAndNavigateToHome() {
        try {
            // Sign out the current user from Firebase
            auth.signOut()

            // Sign out the user from Google
            val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
            googleSignInClient.signOut().addOnCompleteListener {
                // Navigate to the LoginActivity
                val intent = Intent(this, Home::class.java)
                intent.putExtra("emailPackage", "com.example.communitycircuit1")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish() // Finish the current activity to prevent returning to it using the back button
            }
        } catch (e: Exception) {
            // Log the exception for debugging
            Log.e("SettingsActivity", "Sign out failed: ${e.message}", e)
            // Show a toast or message to the user indicating the sign-out failure
            Toast.makeText(this, "Sign out failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}