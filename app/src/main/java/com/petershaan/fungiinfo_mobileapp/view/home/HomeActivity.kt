package com.petershaan.fungiinfo_mobileapp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityHomeBinding
import com.petershaan.fungiinfo_mobileapp.adapter.ViewPagerAdapter
import com.petershaan.fungiinfo_mobileapp.view.about.AboutActivity
import com.petershaan.fungiinfo_mobileapp.view.history.HistoryFragment
import com.petershaan.fungiinfo_mobileapp.view.profil.ProfilFragment
import com.petershaan.fungiinfo_mobileapp.view.upgrade.UpgradeFragment

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

        binding.logo.setOnClickListener{
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupViewPager() {
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(HomeFragment())
        pagerAdapter.addFragment(HistoryFragment())
        pagerAdapter.addFragment(ProfilFragment())
        pagerAdapter.addFragment(UpgradeFragment())
        viewPager.adapter = pagerAdapter
    }

    private fun setupBottomNavigationView() {
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> viewPager.currentItem = 0
                R.id.history -> viewPager.currentItem = 1
                R.id.profile -> viewPager.currentItem = 2
                R.id.diamond -> viewPager.currentItem = 3
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
                    3 -> bottomNav.menu.findItem(R.id.diamond).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

}