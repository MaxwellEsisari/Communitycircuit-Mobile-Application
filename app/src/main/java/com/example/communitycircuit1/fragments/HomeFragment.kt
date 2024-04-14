package com.example.communitycircuit1.fragments

import RvContactsAdapter
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communitycircuit1.AdminPayments
import com.example.communitycircuit1.MainActivity5delivery
import com.example.communitycircuit1.R
import com.example.communitycircuit1.databinding.FragmentHomeBinding
import com.example.communitycircuit1.models.ApiResponse
import com.example.communitycircuit1.models.Contacts
import com.example.communitycircuit1.retrofit.RetrofitClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.communitycircuit1.models.Payment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
      private var _binding : FragmentHomeBinding? = null
      private val binding get() = _binding!!
      private lateinit var contactsList:ArrayList<Contacts>
      private lateinit var firebaseRef : DatabaseReference
      private lateinit var adapter: RvContactsAdapter
      private val campaignTotalMap: MutableMap<String, Double> = mutableMapOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseRef = FirebaseDatabase.getInstance().getReference("contacts")
        contactsList = arrayListOf()
        binding.notificationIcon?.setOnClickListener {
            val intent = Intent(requireContext(), AdminPayments::class.java)
            startActivity(intent)

        }

        binding.aboutUs?.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity5delivery::class.java)
            startActivity(intent)

        }


        binding.shareMeal.setOnClickListener {
            showDonationDialog(requireContext(), "")
        }




        fetchData()

    }

    private fun showDonationDialog(context: Context, campaignBeneficiary: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_donate_layout, null)
        val editTextPhoneNumber = dialogView.findViewById<EditText>(R.id.editTextPhoneNumber)
        val editTextAmount = dialogView.findViewById<EditText>(R.id.editTextAmount)

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("Donate") { dialog, _ ->
                val phoneNumber = editTextPhoneNumber.text.toString()
                val amount = editTextAmount.text.toString()

                // Call your API using Retrofit
                makeDonation(amount,phoneNumber, campaignBeneficiary, context)

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun makeDonation(phone: String, amount: String, campaignBeneficiary: String, context: Context) {
        val apiService = RetrofitClient.apiService
        val call = apiService.pay(phone, amount, campaignBeneficiary)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "Donation Successful"
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to make donation", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Failed to make donation: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun fetchData() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contactsList.clear()
                if (snapshot.exists()) {
                    for (contactSnap in snapshot.children) {
                        val contacts = contactSnap.getValue(Contacts::class.java)
                        contacts?.let {
                            val remainingDays = it.campaignDuration?.let { it1 ->
                                calculateRemainingDays(
                                    it1
                                )
                            }
                            it.remainingDays = "$remainingDays Days Left"
                            contactsList.add(contacts)

                            val payment = contactSnap.getValue(Payment::class.java)
                            val beneficiary = it.campaignBeneficiary ?: "Unknown"
                            val currentTotal = campaignTotalMap[beneficiary] ?: 0.0

                            // Access the amount property from the Payment model
                            val amountString = payment?.amount ?: "0.0"
                            val amount = amountString.toDoubleOrNull() ?: 0.0

                            // Ensure amount is a valid Double before proceeding
                            val total = currentTotal + amount
                            campaignTotalMap[beneficiary] = total
                        }
                    }
                }
                binding.root.post {
                    setupRecyclerView(contactsList, campaignTotalMap) // Update RecyclerView adapter
                }
            }


            private fun calculateRemainingDays(campaignDuration: String): Int {
                // Check if campaignDuration is not empty
                // Check if campaignDuration is not empty
                if (campaignDuration.isNotEmpty()) {
                    // Assume campaignDuration is in days
                    return campaignDuration.toIntOrNull() ?: 0 // Parse campaignDuration to Int, return 0 if unable to parse

                } else {
                    // If campaignDuration is empty, return 0
                    return 0
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(
        contactsList: ArrayList<Contacts>,
        campaignTotalMap: MutableMap<String, Double>
    ) {
        adapter = RvContactsAdapter(contactsList, this.campaignTotalMap)
        binding.rvContacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@HomeFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}