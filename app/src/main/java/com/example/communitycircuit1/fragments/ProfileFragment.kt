package com.example.communitycircuit1.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.communitycircuit1.Home
import com.example.communitycircuit1.R
import com.example.communitycircuit1.Settings
import com.example.communitycircuit1.SignupPage
import com.example.communitycircuit1.databinding.FragmentProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Suppress("UNREACHABLE_CODE")
class ProfileFragment : Fragment() {
    private val settingsButton: Button? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // Firebase authentication instance
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding.textView1.setOnClickListener {
            updateContent("Information for Part 1")
        }

        binding.textView2.setOnClickListener {
            updateContent("Information for Part 2")
        }

        binding.textView3.setOnClickListener {
            updateContent("Information for Part 3")
        }


        binding.settingsIcon.setOnClickListener {
            val intent = Intent(requireContext(), Settings::class.java)
            startActivity(intent)
        }

        val currentUser: FirebaseUser? = auth.currentUser

        if (currentUser != null) {

            // Retrieve the user's creation timestamp
            val creationTimestamp = currentUser.metadata?.creationTimestamp

            // Format the timestamp to display the join date
            val joinDate = formatDate(creationTimestamp)
            val displayName = currentUser.displayName




            // Check for null values before displaying
            val displayText = buildString {
                if (!displayName.isNullOrEmpty()) append(displayName)
                append("\nMember Since: $joinDate\n")

            }

            binding.textView.text = displayText


        }

        val imageView = binding.imageView
        val button = binding.floatingActionButton

        button.setOnClickListener {
            ImagePicker.with(this)
                .crop()                     // Crop image(Optional), Check Customization for more option
                .compress(1024)             // Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  // Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
    }

    private fun updateContent(content: String) {
        binding.textViews.text = content

    }


    private fun formatDate(timestamp: Long?): String {
        timestamp?.let {
            val date = Date(timestamp)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.format(date)
        }
        return "unknown"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer_bottom_button -> {
                // Handle button click here
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = binding.imageView
        imageView.setImageURI(data?.data)
    }
}