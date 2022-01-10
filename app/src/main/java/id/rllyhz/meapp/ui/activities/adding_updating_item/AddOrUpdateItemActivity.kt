package id.rllyhz.meapp.ui.activities.adding_updating_item

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.data.models.Reminder
import id.rllyhz.meapp.ui.features.add_note.AddNoteFragment
import id.rllyhz.meapp.ui.features.add_reminder.AddReminderFragment
import id.rllyhz.meapp.ui.features.picker.DatePickerFragment
import id.rllyhz.meapp.ui.features.picker.TimePickerFragment
import java.util.*

class AddOrUpdateItemActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener,
    TimePickerFragment.DialogTimeListener {
    val sharedViewModel: AddOrUpdateItemSharedViewModel by viewModels()
    var pageNotFound: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        intent.getStringExtra(DESTINATION_PAGE)?.let {
            when (it) {
                ADDING_NOTES_PAGE -> initCurrentFragment(AddNoteFragment())
                UPDATING_NOTES_PAGE -> {
                    initCurrentFragment(Fragment())
                    setDataBeingUpdated(UPDATING_NOTES_PAGE)
                }
                ADDING_REMINDERS_PAGE -> initCurrentFragment(AddReminderFragment())
                UPDATING_REMINDERS_PAGE -> {
                    initCurrentFragment(Fragment())
                    setDataBeingUpdated(UPDATING_REMINDERS_PAGE)
                }
                else -> Unit
            }
        }

        if (pageNotFound) {
            // should alert a warning message!
            finish()
        }
    }

    private fun setDataBeingUpdated(activePage: String) {
        if (activePage == UPDATING_NOTES_PAGE) {
            // val note = intent.extras?.getSerializable(DATA_KEY) as Note
            val note = intent.extras?.getParcelable<Note>(DATA_KEY)

            if (note == null)
                pageNotFound = true
            else
                sharedViewModel.setDataBeingUpdated(note)

        } else if (activePage == UPDATING_REMINDERS_PAGE) {
            val reminder = intent.extras?.getParcelable<Reminder>(DATA_KEY)

            if (reminder == null)
                pageNotFound = true
            else
                sharedViewModel.setDataBeingUpdated(reminder)
        }
    }

    private fun initCurrentFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_frame_add_or_update_item, newFragment)
            .commit()

        pageNotFound = false
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        sharedViewModel.setSelectedTime(calendar.time)
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        sharedViewModel.setSelectedTime(calendar.time)
    }

    companion object {
        const val DESTINATION_PAGE = "DESTINATION_PAGE"
        const val ADDING_NOTES_PAGE = "ADDING_NOTE_PAGE"
        const val UPDATING_NOTES_PAGE = "UPDATING_NOTE_PAGE"
        const val ADDING_REMINDERS_PAGE = "ADDING_REMINDERS_PAGE"
        const val UPDATING_REMINDERS_PAGE = "UPDATING_REMINDERS_PAGE"
        const val DATA_KEY = "DATA_KEY"
    }
}