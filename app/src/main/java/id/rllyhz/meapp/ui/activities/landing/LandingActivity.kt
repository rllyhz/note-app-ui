package id.rllyhz.meapp.ui.activities.landing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.ui.features.splash.SplashFragment
import id.rllyhz.meapp.utils.ResourcesHelper
import id.rllyhz.meapp.utils.setStatusBarColor

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_landing)
        initDefaultUI()

        val splashFragment = SplashFragment()
        replaceCurrentFragment(splashFragment)
    }

    private fun initDefaultUI() {
        setStatusBarColor(ResourcesHelper.getColorFromAttr(this, R.attr.colorPrimary))
    }

    private fun replaceCurrentFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_landing, newFragment)
            .commit()
    }
}