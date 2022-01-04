package id.rllyhz.meapp.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.rllyhz.meapp.R

fun SwipeRefreshLayout.show(state: Boolean) {
    this.isRefreshing = state
}

fun View.lock() {
    isEnabled = false

    when (this) {
        is TextView -> setTextColor(ContextCompat.getColor(context, R.color.greyish))
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