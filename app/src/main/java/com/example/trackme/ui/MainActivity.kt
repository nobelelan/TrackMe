package com.example.trackme.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.trackme.R
import com.example.trackme.databinding.ActivityMainBinding
import com.example.trackme.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var navController: LiveData<NavController>? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null)
            setUpBottomNav()

    }

    // Holds selected position in config change
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNav()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setUpBottomNav() {
        val graphIds = listOf(
            R.navigation.home_nav_graph,
            R.navigation.find_friends_nav_graph
        )
        val controller = binding.bottomNavView.setupWithNavController(
            graphIds,
            supportFragmentManager,
            R.id.nav_host_fragment,
            intent
        )
        controller.observe(this){
            setupActionBarWithNavController(it)
        }
        navController = controller

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.value?.navigateUp()!! || super.onSupportNavigateUp()
    }

}
