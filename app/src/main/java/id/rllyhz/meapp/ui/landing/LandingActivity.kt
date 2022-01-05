package id.rllyhz.meapp.ui.landing

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import id.rllyhz.meapp.R
import id.rllyhz.meapp.data.preferences.UserPreferences
import id.rllyhz.meapp.ui.feature.splash.SplashFragment
import id.rllyhz.meapp.utils.PreferencesKeys
import id.rllyhz.meapp.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class LandingActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
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