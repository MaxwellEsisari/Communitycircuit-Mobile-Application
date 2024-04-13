package com.example.communitycircuit1.fragments

import RvEventsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communitycircuit1.adapter.PendingContactAdapter
import com.example.communitycircuit1.databinding.FragmentSearchBinding
import com.example.communitycircuit1.models.Contacts
import com.example.communitycircuit1.models.Events
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventsList:ArrayList<Events>
    private lateinit var firebaseRef : DatabaseReference
    private lateinit var adapter: RvEventsAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseRef = FirebaseDatabase.getInstance().getReference("events")
        eventsList = arrayListOf()

        // Initialize RecyclerView and Adapter
        adapter = RvEventsAdapter(emptyList()) // Initialize with empty list
        binding.rvEvents.adapter = adapter



        fetchData()

    }



    private fun fetchData() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val events = mutableListOf<Events>() // Use MutableList instead of ArrayList
                for (eventSnap in snapshot.children) {
                    val event = eventSnap.getValue(Events::class.java)
                    event?.let {
                        events.add(event)
                    }
                }
                updateRecyclerView(events)
            }

            override fun onCancelled(error: DatabaseError) {
                // Use requireContext() instead of context
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateRecyclerView(events: List<Events>) {
        if (events.isNotEmpty()) {
            adapter = RvEventsAdapter(events)
            binding.rvEvents.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false) // Set orientation to vertical
                adapter = this@SearchFragment.adapter
            }
        } else {
            Toast.makeText(context, "No events found", Toast.LENGTH_SHORT).show()
        }
    }

}