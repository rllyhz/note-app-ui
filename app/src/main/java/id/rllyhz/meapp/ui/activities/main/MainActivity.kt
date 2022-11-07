package id.rllyhz.meapp.ui.activities.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import id.rllyhz.meapp.R
import id.rllyhz.meapp.databinding.ActivityMainBinding
import id.rllyhz.meapp.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarTheme(false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.apply {
//            val appBarConfiguration = AppBarConfiguration(
//                setOf(
//                    R.id.notesFragment,
//                    R.id.remindersFragment,
//                    R.id.settingsFragment
//                )
//            )
//
//            setupActionBarWithNavController(navController, appBarConfiguration)
            bottomNavigationView.setupWithNavController(navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}