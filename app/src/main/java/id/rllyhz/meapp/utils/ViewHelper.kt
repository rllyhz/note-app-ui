package id.rllyhz.meapp.utils

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.show(state: Boolean) {
    this.isRefreshing = state
}