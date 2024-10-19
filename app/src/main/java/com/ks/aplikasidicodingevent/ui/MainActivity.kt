package com.ks.aplikasidicodingevent.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ks.aplikasidicodingevent.R
import com.ks.aplikasidicodingevent.databinding.ActivityHomeBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private var  currentStatus: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)


        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_upcoming, R.id.navigation_finished
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.topAppBar.title = destination.label

            currentStatus = when(destination.id) {
                R.id.navigation_home -> -1
                R.id.navigation_upcoming -> 1
                R.id.navigation_finished -> 0
                else -> currentStatus
            }

            binding.topAppBar.visibility =
                if (destination.id == R.id.navigation_search) View.GONE else View.VISIBLE

            Log.d("Navigation", "Navigated to: ${destination.label}")
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.search -> {
                    val bundle = Bundle().apply { putInt("status", currentStatus) }  //error
                    navController.navigate(R.id.navigation_search, bundle)
                    true
                } else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }
}