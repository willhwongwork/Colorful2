package com.cp.purecolor2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        MobileAds.initialize(this, "");
        val adRequest = AdRequest.Builder()
                //.addTestDevice("6634CA7A33498E1A2B7DCBD2575C14F3")
                .build()
        adView.loadAd(adRequest)

        setSupportActionBar(main_toolbar)

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        val viewPager = findViewById(R.id.viewPager) as ViewPager
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = MainFragmentPagerAdapter(supportFragmentManager)

        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            viewPager.currentItem = item.order
            true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        viewPager.setOnTouchListener { v, event -> true }

        setRateApp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_rate -> {
            val myUrl = "https://play.google.com/store/apps/details?id=com.cp.purecolor2"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)))
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    private fun setRateApp() {
        AppRate.with(this)
                .setInstallDays(1) // default 10, 0 means install day.
                .setLaunchTimes(10) // default 10
                .setRemindInterval(1) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener { which ->
                    // callback listener.
                    Log.d(MainActivity::class.java.name, Integer.toString(which))
                }
                .monitor()

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this)
    }
}
