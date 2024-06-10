package com.petershaan.fungiinfo_mobileapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityHomeBinding
import com.petershaan.fungiinfo_mobileapp.view.home.HomeFragment
import com.petershaan.fungiinfo_mobileapp.view.history.HistoryFragment
import com.petershaan.fungiinfo_mobileapp.view.profil.ProfilFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNav: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        bottomNav = binding.bottomNav

        setupViewPager()
        setupBottomNavigationView()
    }

    private fun setupViewPager() {
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(HomeFragment())
        pagerAdapter.addFragment(HistoryFragment())
        pagerAdapter.addFragment(ProfilFragment())
        viewPager.adapter = pagerAdapter
    }

    private fun setupBottomNavigationView() {
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> viewPager.currentItem = 0
                R.id.history -> viewPager.currentItem = 1
                R.id.profile -> viewPager.currentItem = 2
            }
            true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNav.menu.findItem(R.id.home).isChecked = true
                    1 -> bottomNav.menu.findItem(R.id.history).isChecked = true
                    2 -> bottomNav.menu.findItem(R.id.profile).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
    companion object {
        private const val TAG = "MainActivity"
    }
}