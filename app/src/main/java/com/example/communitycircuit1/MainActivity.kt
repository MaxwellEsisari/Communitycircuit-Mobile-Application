package com.example.communitycircuit1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import com.example.communitycircuit1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // Check if the user is already logged in
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity2::class.java)) // Redirect to another activity
            finish()
            return
        }

        Handler().postDelayed(
            {
                startActivity(Intent(this,Home::class.java))
                finish()
            },2000
        )

        val textview = findViewById<TextView>(R.id.textview1)
        val label = "community circuit"
        val stringBuilder = StringBuilder()

        Thread{
            for (letter in label){
                stringBuilder.append(letter)
                Thread.sleep(100)
                runOnUiThread{
                  textview.text = stringBuilder.toString()
                }
            }
        }.start()

    }
}