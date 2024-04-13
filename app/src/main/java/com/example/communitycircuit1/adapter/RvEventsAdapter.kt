import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.communitycircuit1.MainActivity3
import com.example.communitycircuit1.databinding.AddEventsLayoutBinding
import com.example.communitycircuit1.models.Events
import com.squareup.picasso.Picasso

class RvEventsAdapter(private var eventsList: List<Events>) :
    RecyclerView.Adapter<RvEventsAdapter.ViewHolder>() {

    class ViewHolder(val binding: AddEventsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AddEventsLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEvent = eventsList[position]
        holder.binding.apply {
            tvIdItems.text = currentEvent.id ?: ""
            eventName.text = currentEvent.eveName ?: ""
            eventVenue.text = currentEvent.eveVenue ?: ""
            evenTime.text = currentEvent.evenTime ?: ""

            // Load image using Picasso
            Picasso.get().load(currentEvent.imguri).into(imgItem)

            // Set click listener for the image
            imgItem.setOnClickListener {
                val intent = Intent(holder.itemView.context, MainActivity3::class.java).apply {
                    putExtra("image_url", currentEvent.imguri)
                    putExtra("name", currentEvent.eveName)
                    putExtra("evenTime", currentEvent.evenTime)

                    // Add more data here if needed
                }
                holder.itemView.context.startActivity(intent)
            }
        }
    }


}
