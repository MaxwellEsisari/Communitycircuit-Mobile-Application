package com.example.communitycircuit1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.communitycircuit1.databinding.ActivityMainHomeBinding
import com.google.firebase.auth.FirebaseAuth

class MainHome : AppCompatActivity() {
    private lateinit var binding: ActivityMainHomeBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        binding.showQuestionsButton.setOnClickListener{
            startActivity(Intent(this,About::class.java))
        }
        binding.skip.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }

    }
}