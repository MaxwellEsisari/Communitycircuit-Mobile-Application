import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.communitycircuit1.MainActivity3
import com.example.communitycircuit1.R
import com.example.communitycircuit1.databinding.RvContactsItemBinding
import com.example.communitycircuit1.models.ApiResponse
import com.example.communitycircuit1.models.Contacts
import com.example.communitycircuit1.retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RvContactsAdapter(private var contactList: ArrayList<Contacts>,private val campaignTotalMap: Map<String, Double>) :
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

            // Calculate and display total amount paid for the beneficiary
            val beneficiaryTotalAmount = campaignTotalMap[currentItem.campaignBeneficiary] ?: 0.0
            textBeneficiaryTotal.text = "Total Amount Paid: $beneficiaryTotalAmount"

            shareMeal.setOnClickListener {
                showDonationDialog(holder.itemView.context, currentItem.campaignBeneficiary ?: "")
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

    private fun showDonationDialog(context: Context, campaignBeneficiary: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_donate_layout, null)
        val editTextPhoneNumber = dialogView.findViewById<EditText>(R.id.editTextPhoneNumber)
        val editTextAmount = dialogView.findViewById<EditText>(R.id.editTextAmount)

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("Donate") { dialog, _ ->
                val amount = editTextAmount.text.toString()
                val phoneNumber = editTextPhoneNumber.text.toString()


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
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to make donation", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to make donation: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
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

