package id.rllyhz.meapp.utils

import android.annotation.SuppressLint
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.data.models.Reminder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DataHelper {
    fun getAllNotes(): List<Note> {
        val notes = ArrayList<Note>()

        notes.add(Note(1, "test 1", "lorem ipsum", Date().time, Date().time))
        notes.add(
            Note(
                1,
                "test 2",
                "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                1,
                "note 1",
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                1,
                "note 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do",
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                1,
                "test 3",
                "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                1,
                "test 4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do",
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                1,
                "note 3",
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                Date().time,
                Date().time
            )
        )
        notes.add(
            Note(
                1,
                "note 4",
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
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
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )
        reminders.add(
            Reminder(
                1,
                "reminder 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do.",
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )
        reminders.add(
            Reminder(
                1,
                "reminder 3",
                "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )
        reminders.add(
            Reminder(
                1,
                "reminder 4",
                "ut aliquip ex ea commodo consequat.",
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )

        return reminders
    }
}

@SuppressLint("SimpleDateFormat")
private fun convertLongToDateString(time: Long): String = Date(time).run {
    val date = Date(time)
    val format = SimpleDateFormat("MMM dd, yyyy")

    format.format(date)
}

fun Note.formattedUpdatedAt(): String = convertLongToDateString(updatedAt)

fun Reminder.formattedNotifyingAt(): String = convertLongToDateString(notifyingAt)