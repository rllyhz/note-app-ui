package id.rllyhz.meapp.utils

import android.annotation.SuppressLint
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.data.models.Reminder
import id.rllyhz.meapp.vo.LevelOfImportance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DataHelper {
    fun getAllNotes(): List<Note> {
        val notes = ArrayList<Note>()

        notes.add(
            Note(
                1,
                "test 1",
                "lorem ipsum",
                LevelOfImportance.LESS_IMPORTANT,
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                2,
                "test 2",
                "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                LevelOfImportance.NORMAL,
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                3,
                "note 1",
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                LevelOfImportance.NORMAL,
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                4,
                "note 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do",
                LevelOfImportance.IMPORTANT,
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                5,
                "test 3",
                "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                LevelOfImportance.IMPORTANT,
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                6,
                "test 4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do",
                LevelOfImportance.IMPORTANT,
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                7,
                "note 3",
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                LevelOfImportance.NORMAL,
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                8,
                "note 4",
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                LevelOfImportance.NORMAL,
                Date().time,
                Date().time
            )
        )

        return notes
    }

    fun getAllReminders(): List<Reminder> {
        val reminders = ArrayList<Reminder>()

        reminders.add(
            Reminder(
                1,
                "reminder 1",
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                false,
                Calendar.getInstance().timeInMillis,
                LevelOfImportance.NORMAL,
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )
        reminders.add(
            Reminder(
                1,
                "reminder 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do.",
                false,
                Calendar.getInstance().timeInMillis,
                LevelOfImportance.LESS_IMPORTANT,
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )
        reminders.add(
            Reminder(
                1,
                "reminder 3",
                "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                false,
                Calendar.getInstance().timeInMillis,
                LevelOfImportance.LESS_IMPORTANT,
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )
        reminders.add(
            Reminder(
                1,
                "reminder 4",
                "ut aliquip ex ea commodo consequat.",
                false,
                Calendar.getInstance().timeInMillis,
                LevelOfImportance.IMPORTANT,
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )

        return reminders
    }
}

@SuppressLint("SimpleDateFormat")
private fun convertLongToDateString(time: Long): String = Date(time).run {
    SimpleDateFormat("MMM dd, yyyy").format(this)
}

@SuppressLint("SimpleDateFormat")
private fun convertLongToTimeString(time: Long): String = Date(time).run {
    SimpleDateFormat("HH:mm").format(this)
}

fun Note.formattedUpdatedAt(): String = convertLongToDateString(updatedAt)

fun Date.toDateString(): String = convertLongToDateString(time)

fun Date.toTimeString(): String = convertLongToTimeString(time)

fun Reminder.formattedNotifyingAt(): String = convertLongToDateString(notifyingAt)

fun Long.getTimeAgo(): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val currentCalendar = Calendar.getInstance()

    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentMonth = currentCalendar.get(Calendar.MONTH)
    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
    val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = currentCalendar.get(Calendar.MINUTE)

    return if (year < currentYear) {
        val interval = currentYear - year
        if (interval == 1) "$interval year ago" else "$interval years ago"
    } else if (month < currentMonth) {
        val interval = currentMonth - month
        if (interval == 1) "$interval month ago" else "$interval months ago"
    } else if (day < currentDay) {
        val interval = currentDay - day
        if (interval == 1) "$interval day ago" else "$interval days ago"
    } else if (hour < currentHour) {
        val interval = currentHour - hour
        if (interval == 1) "$interval hour ago" else "$interval hours ago"
    } else if (minute < currentMinute) {
        val interval = currentMinute - minute
        if (interval == 1) "$interval minute ago" else "$interval minutes ago"
    } else {
        "A moment ago"
    }
}

fun String.capitalize(): String =
    replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }