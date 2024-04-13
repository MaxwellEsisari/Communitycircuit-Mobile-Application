package com.example.communitycircuit1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.communitycircuit1.adapter.CampaignsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.example.communitycircuit1.databinding.ActivityMainActivity5deliveryBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class MainActivity5delivery : AppCompatActivity() {
    private lateinit var binding: ActivityMainActivity5deliveryBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: CampaignsPagerAdapter
    private val autoScrollHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainActivity5deliveryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val view = binding.root
        setContentView(binding.root)
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
    }

    // Method to fetch your list of image resources
    private fun getImageList(): List<Int> {
        // Return your list of image resources here
        return listOf(R.drawable.deliveryfour, R.drawable.deliveryimg,
            R.drawable.deliverytwo, R.drawable.deliverythree,
            )
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