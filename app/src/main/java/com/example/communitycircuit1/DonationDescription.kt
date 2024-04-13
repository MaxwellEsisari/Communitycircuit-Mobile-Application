package com.example.communitycircuit1

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.communitycircuit1.databinding.ActivityDonationDescriptionBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class DonationDescription : AppCompatActivity() {
    private lateinit var binding: ActivityDonationDescriptionBinding
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDonationDescriptionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        // Retrieve data passed from the previous activity
        val imageUrl = intent.getStringExtra("image_url")
        val name = intent.getStringExtra("name")
        val tagName = intent.getStringExtra("tagName") // Retrieve tagline
        val campaignAbout = intent.getStringExtra("campaignAbout") // Retrieve campaignAbout
        val campaignBeneficiary = intent.getStringExtra("campaignBeneficiary") // Retrieve campaignBeneficiary

        val remainingDays = intent.getIntExtra("remainingDays", 0)

        Log.d("MainActivity3", "Remaining Days: $remainingDays")


        // Display the image and its details
        Picasso.get().load(imageUrl).into(binding.imageV)
        binding.textView3.text = name
        binding.tagName.text = tagName // Set tagline
        binding.campaignAbout.text = campaignAbout // set campaign About

        binding.textBeneficiary.text = campaignBeneficiary // set campaign About


        binding.textViewCountdown.text = "Remaining Days: $remainingDays"


        // Initialize and start the countdown timer

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
//        val currentUser: FirebaseUser? = auth.currentUser

        startCountdownTimer(remainingDays)
    }

    private fun startCountdownTimer(remainingDays: Int) {
        countDownTimer = object : CountDownTimer(remainingDays * 24 * 60 * 60 * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val days = millisUntilFinished / (24 * 60 * 60 * 1000)
                val hours = millisUntilFinished % (24 * 60 * 60 * 1000) / (60 * 60 * 1000)
                val minutes = millisUntilFinished % (60 * 60 * 1000) / (60 * 1000)
                val seconds = millisUntilFinished % (60 * 1000) / 1000
                binding.textViewCountdown.text = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds)
            }

            override fun onFinish() {
                binding.textViewCountdown.text = "00:00:00:00" // Update UI when timer finishes
            }
        }
        countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel() // Cancel the countdown timer to avoid memory leaks
    }
}