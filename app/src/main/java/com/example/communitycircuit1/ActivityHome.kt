package com.example.communitycircuit1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.communitycircuit1.databinding.ActivityHomeBinding
import com.example.communitycircuit1.fragments.CampaignsFragment
import com.example.communitycircuit1.fragments.HomeFragment
import com.example.communitycircuit1.fragments.InboxFragment
import com.example.communitycircuit1.fragments.ProfileFragment
import com.example.communitycircuit1.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityHome : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.campaigns -> replaceFragment(CampaignsFragment())
                R.id.inbox -> replaceFragment(InboxFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_Layout, fragment)
            fragmentTransaction.commit()


    }
}
