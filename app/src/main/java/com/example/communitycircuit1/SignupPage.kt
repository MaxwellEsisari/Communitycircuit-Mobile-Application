package com.example.communitycircuit1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.communitycircuit1.databinding.ActivitySignupPageBinding

class SignupPage : AppCompatActivity() {
    private lateinit var binding: ActivitySignupPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnEmail.setOnClickListener{
            startActivity(Intent(this,SignUpPageEmail::class.java))
        }
    }
}