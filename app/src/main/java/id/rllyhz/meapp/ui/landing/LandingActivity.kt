package id.rllyhz.meapp.ui.landing

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.ui.feature.splash.SplashFragment

class LandingActivity : AppCompatActivity() {
    val viewModel: LandingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val splashFragment = SplashFragment()
        replaceCurrentFragment(splashFragment)
    }

    private fun replaceCurrentFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_landing, newFragment)
            .commit()
    }
}