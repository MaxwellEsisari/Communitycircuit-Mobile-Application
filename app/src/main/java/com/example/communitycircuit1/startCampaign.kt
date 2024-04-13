package com.example.communitycircuit1

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.communitycircuit1.databinding.ActivityStartCampaignBinding
import com.example.communitycircuit1.fragments.HomeFragment
import com.example.communitycircuit1.models.Contacts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class StartCampaign : AppCompatActivity() {
    private lateinit var firebaseRef: DatabaseReference
    private lateinit var pendingContactsRef: DatabaseReference // Separate reference for pending_contacts
    private lateinit var storageRef: StorageReference
    private lateinit var binding: ActivityStartCampaignBinding

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartCampaignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseRef = FirebaseDatabase.getInstance().getReference("contacts")
        pendingContactsRef = FirebaseDatabase.getInstance().getReference("pending_contacts") // Initialize pending_contacts reference
        storageRef = FirebaseStorage.getInstance().getReference("Images")

        val categories = arrayOf(
            "Education", "Medical",
            "Children", "Business",
            "Environment", "Social Services"
        )

        binding.spinnerCategory.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, categories)

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imgAdd.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        binding.buttonChooseImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.buttonSubmit.setOnClickListener {

            savedData()

            // Navigate to HomeFragment
//            navigateToHomeFragment()

        }


    }

//    private fun navigateToHomeFragment() {
//        val intent = Intent(this, HomeFragment::class.java)
//        startActivity(intent)
//        finish() // Optional: Close the current activity if needed
//    }

    private fun savedData() {
        val campaignTitle = binding.editTextCampaignTitle.text.toString()
        val campaignTagline = binding.editTextCampaignTagline.text.toString()
        val campaignDuration = binding.editTextCampaignDuration.text.toString()
        val selectedCategory = binding.spinnerCategory.selectedItem.toString()
        val campaignAbout = binding.editTextAboutCampaign.text.toString()
        val campaignBeneficiary = binding.editTextBeneficiary.text.toString()
        val campaignNumber = binding.editTextNumber.text.toString()


        if (campaignTitle.isEmpty() || campaignDuration.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        val contactId = pendingContactsRef.push().key!! // Use pending_contacts reference

        uri?.let {
            storageRef.child(contactId).putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            val imgUrl = url.toString()

                            val contacts = Contacts(
                                id = contactId,
                                name = campaignTitle,
                                imguri = imgUrl,
                                tagName = campaignTagline,
                                category = selectedCategory,
                                campaignDuration = campaignDuration,
                                campaignAbout= campaignAbout,
                                campaignBeneficiary=campaignBeneficiary,
                                campaignNumber=campaignNumber,

                                        approved = false // Set approved status to false
                            )

                            pendingContactsRef.child(contactId).setValue(contacts)
                                .addOnCompleteListener {
                                    Toast.makeText(this, "Data submitted for approval", Toast.LENGTH_SHORT).show()
                                    // Clear the form fields or perform any other necessary actions
                                    binding.editTextCampaignTitle.text.clear()
                                    binding.editTextCampaignTagline.text.clear()
                                    binding.editTextCampaignDuration.text.clear()
                                    binding.editTextAboutCampaign.text.clear()
                                    binding.editTextBeneficiary.text.clear()
                                    binding.editTextNumber.text.clear()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                }
        }
    }
}