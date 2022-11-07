package id.rllyhz.meapp.ui.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

open class BaseActivity : AppCompatActivity() {

    protected fun setNoNightModeUI() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    protected fun setStatusBarTheme(isDark: Boolean) {
        val currentFlags = window.decorView.systemUiVisibility

        window.decorView.systemUiVisibility = when {
            isDark -> currentFlags or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or -(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            else -> currentFlags or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}