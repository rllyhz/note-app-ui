package id.rllyhz.meapp.ui.activities.landing

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.ui.activities.main.MainActivity
import id.rllyhz.meapp.ui.base.BaseActivity
import id.rllyhz.meapp.ui.features.landing.FirstLandingFragment
import id.rllyhz.meapp.ui.features.landing.SecondLandingFragment
import id.rllyhz.meapp.ui.features.landing.SplashFragment
import id.rllyhz.meapp.utils.ResourcesHelper
import id.rllyhz.meapp.utils.setStatusBarColor

class LandingActivity : BaseActivity() {
    private var currentLandingIndex = 0
    private var shouldShowOnBoardingPages = true

    private val viewModel: LandingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNoNightModeUI()
        setStatusBarTheme(true)

        viewModel.shouldShowOnBoardingPages(this).observe(this) {
            shouldShowOnBoardingPages = it
        }

        setContentView(R.layout.activity_landing)
        initDefaultUI()

        // first, show splash
        updateCurrentFragment(SplashFragment(), true)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (shouldShowOnBoardingPages) {
                    updateCurrentFragment(FirstLandingFragment())
                } else {
                    currentLandingIndex++
                    next()
                }
            },
            3000
        )
    }

    fun next() {
        if (currentLandingIndex == 0) {
            updateCurrentFragment(SecondLandingFragment())
            currentLandingIndex++
        } else {
            viewModel.shouldShowOnBoardingPages(this, false)
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun initDefaultUI() {
        setStatusBarColor(ResourcesHelper.getColorFromAttr(this, R.attr.colorPrimary))
    }

    private fun updateCurrentFragment(newFragment: Fragment, shouldReplace: Boolean = false) {
        supportFragmentManager.beginTransaction()
            .run {
                if (shouldReplace) replace(
                    R.id.main_frame_landing,
                    newFragment
                ) else add(R.id.main_frame_landing, newFragment)
            }
            .commit()
    }
}