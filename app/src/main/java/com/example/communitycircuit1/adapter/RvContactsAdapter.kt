import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.communitycircuit1.MainActivity3
import com.example.communitycircuit1.databinding.RvContactsItemBinding
import com.example.communitycircuit1.fragments.HomeFragment
import com.example.communitycircuit1.models.Contacts
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RvContactsAdapter(private var contactList: ArrayList<Contacts>) :
    RecyclerView.Adapter<RvContactsAdapter.ViewHolder>() {

    class ViewHolder(val binding: RvContactsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvContactsItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = contactList[position]
        holder.binding.apply {
            textViewCampaignTitle00001.text = currentItem.name ?: ""
            tvIdItem.text = currentItem.id ?: ""
            textViewRemainingDays.text = currentItem.campaignDuration ?: ""
            textBeneficiary.text = currentItem.campaignBeneficiary ?: ""
            textCampaignAbout.text = currentItem.campaignAbout ?: ""
            textViewRemainingDays.text = currentItem.remainingDays ?: ""

            // Set click listener for the donate button
            shareMeal.setOnClickListener {
                val homeFragment = HomeFragment()
                homeFragment.showDonationDialog()
            }




            // Calculate remaining days
            val remainingDays = calculateRemainingDays(currentItem.campaignDuration)
            Picasso.get().load(currentItem.imguri).into(imgItem)

            // Set click listener for the image
            imgItem.setOnClickListener {
                val intent = Intent(holder.itemView.context, MainActivity3::class.java).apply {
                    putExtra("image_url", currentItem.imguri)
                    putExtra("name", currentItem.name)
                    putExtra("tagName", currentItem.tagName)
                    putExtra("category", currentItem.category)
                    putExtra("campaignDuration", currentItem.campaignDuration)
                    putExtra("campaignAbout", currentItem.campaignAbout) // Pass campaignAbout
                    putExtra("campaignBeneficiary",currentItem.campaignBeneficiary) // Pass campaignBeneficiary
                    putExtra("campaignNumber", currentItem.campaignNumber) // Pass campaignAbout

                }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    fun searchDataList(searchList: List<Contacts>) {
        contactList.clear()
        contactList.addAll(searchList)
        notifyDataSetChanged()
    }


    // Function to calculate remaining days
    private fun calculateRemainingDays(campaignDuration: String?): Int {
        if (campaignDuration.isNullOrEmpty()) {
            return 0
        }

        // Assuming campaign duration is in format "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val endDate = dateFormat.parse(campaignDuration)
            val currentDate = Date()

            // Calculate remaining days
            val remainingTime = endDate.time - currentDate.time
            val remainingDays =
                remainingTime / (1000 * 60 * 60 * 24) // Convert milliseconds to days

            return if (remainingDays >= 0) {
                remainingDays.toInt() // If remaining days is positive, return it
            } else {
                0 // If campaign has ended, return 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0 // Return 0 if parsing or calculation fails
    }
}

