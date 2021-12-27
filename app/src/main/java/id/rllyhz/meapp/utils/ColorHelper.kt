package id.rllyhz.meapp.utils

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import id.rllyhz.meapp.R

object ColorHelper {
    @ColorRes
    private var color: Int = R.color.soft_grey

    private fun randomizeColor() {
        when (color) {
            R.color.soft_blue -> {
                color = R.color.soft_pink
            }
            R.color.soft_pink -> {
                color = R.color.soft_orange
            }
            R.color.soft_orange -> {
                color = R.color.soft_grey
            }
            R.color.soft_grey -> {
                color = R.color.soft_blue
            }
        }
    }

    @ColorInt
    fun getRandomColor(context: Context): Int {
        randomizeColor()
        return ContextCompat.getColor(context, color)
    }
}