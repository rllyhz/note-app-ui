package id.rllyhz.meapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import id.rllyhz.meapp.R
import id.rllyhz.meapp.vo.LevelOfImportance

object ResourcesHelper {
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
                color = R.color.soft_green
            }
            R.color.soft_green -> {
                color = R.color.soft_pink_2
            }
            R.color.soft_pink_2 -> {
                color = R.color.soft_purple
            }
            R.color.soft_purple -> {
                color = R.color.soft_blue
            }
        }
    }

    @ColorInt
    fun getColor(context: Context, @ColorRes colorId: Int): Int =
        ContextCompat.getColor(context, colorId)

    @ColorInt
    fun getRandomColor(context: Context): Int {
        randomizeColor()
        return ContextCompat.getColor(context, color)
    }

    private fun getDrawable(context: Context, @DrawableRes drawableId: Int): Drawable? =
        ContextCompat.getDrawable(context, drawableId)

    private fun getStringArray(context: Context, @ArrayRes stringArrayId: Int): List<String> =
        context.resources.getStringArray(stringArrayId).toList()

    fun getBackgroundBadge(context: Context): Drawable? =
        getDrawable(context, R.drawable.bg_badge)

    fun getBackgroundBadgeForLevelOfImportance(
        context: Context,
        levelOfImportance: Int
    ): Drawable? {
        val bgBadgeColor = when (levelOfImportance) {
            LevelOfImportance.NORMAL -> getColor(context, R.color.soft_green)
            LevelOfImportance.IMPORTANT -> getColor(context, R.color.soft_pink)
            else -> getColor(context, R.color.soft_blue)
        }

        return getBackgroundBadge(context)?.let {
            it.setTint(bgBadgeColor)
            it
        }
    }

    fun getDescriptionForLevelOfImportanceIndex(
        context: Context,
        levelOfImportanceIndex: Int
    ): String =
        getStringArray(context, R.array.level_of_importance_keys).let {
            it[levelOfImportanceIndex]
        }
}