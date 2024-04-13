package com.example.communitycircuit1.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.communitycircuit1.MainActivity3
import com.example.communitycircuit1.databinding.RvPendingItemsBinding
import com.example.communitycircuit1.models.PendingContact

import com.squareup.picasso.Picasso

class PendingContactAdapter(private var contactList: List<PendingContact>) :
    RecyclerView.Adapter<PendingContactAdapter.ViewHolder>() {
    var onApproveClickListener: ((PendingContact) -> Unit)? = null


    class ViewHolder(val binding: RvPendingItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvPendingItemsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    // This function updates the list of contacts in the adapter
    fun updateList(newList: List<PendingContact>) {
        contactList = newList
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = contactList[position] // Access the current contact from the contactList
        holder.binding.apply {
            tvCampaign.text = currentItem.name ?: ""
            tvIdItems.text = currentItem.id ?: ""
            textBeneficiary.text = currentItem.campaignBeneficiary ?: ""


            Picasso.get().load(currentItem.imguri).into(imgItem)


            // Set click listener for the image
            imgItem.setOnClickListener {
                // Pass the information to MainActivity3
                val intent = Intent(holder.itemView.context, MainActivity3::class.java)
                intent.putExtra("image_url", currentItem.imguri) // Pass image URL
                intent.putExtra("name", currentItem.name) // Pass other relevant data
                intent.putExtra("tagName", currentItem.tagName) // Pass other relevant data
                intent.putExtra("category", currentItem.category) // Pass category
                intent.putExtra("campaignDuration", currentItem.campaignDuration) // Pass campaignDuration
                intent.putExtra("campaignAbout", currentItem.campaignAbout) // Pass campaignAbout
                intent.putExtra("campaignBeneficiary", currentItem.campaignBeneficiary) // Pass campaignAbout
                intent.putExtra("campaignNumber", currentItem.campaignNumber) // Pass campaignAbout




                holder.itemView.context.startActivity(intent) // Start the MainActivity3
            }
            // Set click listener for the "Approve" button
            btnApprove.setOnClickListener {
                onApproveClickListener?.invoke(currentItem)
            }

        }
    }
}