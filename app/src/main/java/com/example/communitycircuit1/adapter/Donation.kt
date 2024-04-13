package com.example.communitycircuit1.adapter

data class Donation(
    val amount: Double,
    val donorName: String,
    val recipientName: String,  // Add recipient field
    val date: String
    // Add more fields as needed
)
