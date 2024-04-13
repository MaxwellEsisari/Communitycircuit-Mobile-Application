package com.example.communitycircuit1.models

data class Contacts(
    val id: String? = null,
    val name: String? = null,
    val imguri: String? = null,
    val tagName: String? = null,
    val category: String? = null,
    val campaignDuration: String? = null,
    val campaignAbout: String? = null,
    val campaignBeneficiary: String? = null,
    val campaignNumber: String? =null,
    var remainingDays: String? = null,
//    val role: String, // Role can be "admin" or "normal"
    var approved: Boolean = false // Initially, contacts are not approved
)

//data class Post(
//    val id: String,
//    val title: String,
//    val content: String,
//    val userId: String, // The user who created the post
//    var approved: Boolean = false // Initially, posts are not approved
//)
//
//data class User(
//    val userId: String,
//    val username: String,
//    val role: String, // Role can be "admin" or "normal"
//    var approved: Boolean = false // Initially, users are not approved
//)


