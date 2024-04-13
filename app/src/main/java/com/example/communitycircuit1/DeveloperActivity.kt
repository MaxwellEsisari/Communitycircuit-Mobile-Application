package com.example.communitycircuit1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.communitycircuit1.adapter.PendingContactAdapter
import com.example.communitycircuit1.databinding.ActivityDeveloperBinding
import com.example.communitycircuit1.fragments.SearchFragment
import com.example.communitycircuit1.models.Contacts
import com.example.communitycircuit1.models.PendingContact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.arrayListOf


class DeveloperActivity : AppCompatActivity() {

    // Firebase references
    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDeveloperBinding

    // RecyclerView and adapter
    private lateinit var adapter: PendingContactAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseRef = FirebaseDatabase.getInstance().getReference("pending_contacts")



        // Initialize RecyclerView and Adapter
        adapter = PendingContactAdapter(emptyList()) // Initialize with empty list
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter


        // Fetch data from Firebase
        fetchData()
        // Set click listener for approve button
        adapter.onApproveClickListener = { pendingContact ->
            approveContact(pendingContact)
        }

        binding.btnAddEvent.setOnClickListener{
            startActivity(Intent(this, Event::class.java))
        }
    }

    private fun fetchData() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Firebase", "onDataChange triggered")
                val pendingContacts = mutableListOf<PendingContact>() // Initialize list to hold pending contacts
                for (contactSnap in snapshot.children) {
                    Log.d("Firebase", "Contact ID: ${contactSnap.key}")
                    val pendingContact = contactSnap.getValue(PendingContact::class.java)
                    pendingContact?.let {
                        Log.d("Firebase", "Pending Contact: $it")
                        pendingContacts.add(it)
                    }
                }
                adapter.updateList(pendingContacts) // Update adapter with new data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DeveloperActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun approveContact(pendingContact: PendingContact) {
        Log.d("DeveloperActivity", "Approving contact: $pendingContact")

        Log.d("DeveloperActivity", "Approving contact: $pendingContact")

        databaseRef.child(pendingContact.id.toString()).removeValue()

        // Add to contacts
        val contactsRef = FirebaseDatabase.getInstance().getReference("contacts")
        val newContactId = contactsRef.push().key
        val newContact = Contacts(
            newContactId,
            pendingContact.name,
            pendingContact.imguri,
            pendingContact.tagName,
            pendingContact.category,
            pendingContact.campaignDuration,
            pendingContact.campaignAbout,
            pendingContact.campaignBeneficiary, // Ensure correct mapping
            pendingContact.campaignNumber,

            approved = true
        )

        newContactId?.let {
            contactsRef.child(it).setValue(newContact)
        }

        // Update the approved status to "Yes" and save it to the database
        val updatedPendingContact = pendingContact.copy(approved = true)
        val updatedPendingContactId = pendingContact.id.toString()
        databaseRef.child(updatedPendingContactId).setValue(updatedPendingContact)

        Toast.makeText(this, "Contact approved", Toast.LENGTH_SHORT).show()

        // Fetch updated data and update RecyclerView adapter
        fetchData()
    }
}

