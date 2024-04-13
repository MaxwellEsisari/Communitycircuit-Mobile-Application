package com.example.communitycircuit1.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.communitycircuit1.R
import com.example.communitycircuit1.StartCampaign
import com.example.communitycircuit1.adapter.CampaignsPagerAdapter
import com.example.communitycircuit1.databinding.FragmentCampaignsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CampaignsFragment : Fragment() {

    private var _binding: FragmentCampaignsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: CampaignsPagerAdapter
    private val autoScrollHandler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCampaignsBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize ViewPager2 and TabLayout
        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        // Create adapter with a list of images and set it to ViewPager2
        val imageList = getImageList() // Fetch your list of image resources
        adapter = CampaignsPagerAdapter(imageList)
        viewPager.adapter = adapter

        // Attach TabLayout to ViewPager2 using TabLayoutMediator
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        // Start auto-scrolling
        startAutoScroll()

        return view
    }

    // Method to fetch your list of image resources
    private fun getImageList(): List<Int> {
        // Return your list of image resources here
        return listOf(R.drawable.kindness, R.drawable.poverty,
            R.drawable.kindness, R.drawable.poverty,
            R.drawable.payment, R.drawable.take)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startCampaigns.setOnClickListener {
            val intent = Intent(requireActivity(), StartCampaign::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startAutoScroll() {
        val autoScrollRunnable = object : Runnable {
            override fun run() {
                val nextItem = if (viewPager.currentItem == adapter.itemCount - 1) 0 else viewPager.currentItem + 1
                viewPager.setCurrentItem(nextItem, true)
                autoScrollHandler.postDelayed(this, AUTO_SCROLL_INTERVAL)
            }
        }

        autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INITIAL_DELAY)
    }

    companion object {
        private const val AUTO_SCROLL_INTERVAL: Long = 3000 // Auto-scroll interval in milliseconds
        private const val AUTO_SCROLL_INITIAL_DELAY: Long = 3000 // Initial delay before auto-scroll starts
    }
}