package com.example.communitycircuit1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.communitycircuit1.databinding.ActivityEventBinding
import com.example.communitycircuit1.models.Events
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Event : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding
    private lateinit var firebaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        firebaseRef = FirebaseDatabase.getInstance().getReference("events")
        storageRef = FirebaseStorage.getInstance().getReference("Images")



        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imgAdd.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        binding.buttonChooseImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        // Set click listener for submit button
        binding.buttonSubmit.setOnClickListener {
            saveEventData()
        }
        binding.editEvenTime.setOnClickListener {
            showTimePickerDialog()
        }

        binding.editEvenTime.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                // Set the selected time to the EditText
                binding.editEvenTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
            },
            hourOfDay,
            minute,
            true // 24 hour format
        )

        timePickerDialog.show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, dayOfMonth ->
                // Set the selected date to the EditText
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.editEvenTime.setText(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
    }



    private fun saveEventData() {
        val eventName = binding.editEventTitle.text.toString().trim()
        val eventVenue = binding.editEventVenue.text.toString().trim()
        val evenTime = binding.editEvenTime.text.toString().trim()



        if (eventName.isEmpty() || eventVenue.isEmpty() || evenTime.isEmpty() || uri == null) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        val eventId = firebaseRef.push().key ?: "" // Generate unique event ID
        var events: Events
        uri?.let{
            storageRef.child(eventId).putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { uri ->
                            val imgUrl = uri.toString() // Get image URL from Firebase download URL
                            val events = Events(id = eventId, eveName = eventName, eveVenue = eventVenue,evenTime = evenTime, imguri = imgUrl)

                            // Save event data to Firebase
                            firebaseRef.child(eventId).setValue(events)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Event saved successfully", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Failed to save event: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                }

        }





    }
}
