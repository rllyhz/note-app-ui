package id.rllyhz.meapp.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.rllyhz.meapp.R

// Activity and Views
fun AppCompatActivity.setStatusBarColor(@ColorInt color: Int) {
    window.statusBarColor = color
}

fun View.lock() {
    isEnabled = false

    when (this) {
        is TextView -> setTextColor(ContextCompat.getColor(context, R.color.grey_2))
    }
}

fun View.unlock(@ColorRes textColorIfNeeded: Int?) {
    isEnabled = true

    when (this) {
        is TextView -> textColorIfNeeded?.let {
            setTextColor(
                ContextCompat.getColor(
                    context,
                    textColorIfNeeded
                )
            )
        }
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}


// UIs
fun SwipeRefreshLayout.show(state: Boolean) {
    this.isRefreshing = state
}