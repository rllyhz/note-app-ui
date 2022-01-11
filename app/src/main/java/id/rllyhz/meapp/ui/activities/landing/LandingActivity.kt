package id.rllyhz.meapp.ui.activities.landing

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import id.rllyhz.meapp.R
import id.rllyhz.meapp.data.preferences.AppPreferences
import id.rllyhz.meapp.ui.activities.main.MainActivity
import id.rllyhz.meapp.ui.features.splash.SplashFragment
import id.rllyhz.meapp.utils.ResourcesHelper
import kotlinx.coroutines.delay

class LandingActivity : AppCompatActivity() {
    private val viewModel: LandingViewModel by viewModels()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_landing)
        initDefaultUI()

        viewModel.shouldShownOnBoardingPage(applicationContext)
            .observe(this) { shouldShowOnBoardingPage ->
                if (!shouldShowOnBoardingPage) {
                    lifecycleScope.launchWhenResumed {
                        delay(3000)
                        gotoMainPage()
                    }
                }
            }

        val splashFragment = SplashFragment()
        replaceCurrentFragment(splashFragment)
    }

    private fun initDefaultUI() {
        setStatusBarColor(ResourcesHelper.getColorFromAttr(this, R.attr.colorPrimary))
    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        window.statusBarColor = color
    }

    fun getAppPreferences(): LiveData<AppPreferences> =
        viewModel.getAppPreferences(this)

    fun setUserPreferencesByStringKey(
        key: Preferences.Key<String>,
        value: String
    ) = viewModel.setUserPreferencesByStringKey(this, key, value)

    fun setUserPreferencesByBooleanKey(
        key: Preferences.Key<Boolean>,
        value: Boolean
    ) = viewModel.setUserPreferencesByBooleanKey(this, key, value)

    fun initLandingPageToAlwaysShowMainPage() {
        viewModel.setShouldShowOnBoardingPage(this, false)
    }

    private fun gotoMainPage() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also {
            startActivity(it)
            finish()
        }
    }

    private fun replaceCurrentFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_landing, newFragment)
            .commit()
    }
}