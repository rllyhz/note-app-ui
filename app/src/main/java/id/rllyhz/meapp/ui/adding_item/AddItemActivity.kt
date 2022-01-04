package id.rllyhz.meapp.ui.adding_item

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.ui.feature.add_note.AddNoteFragment
import id.rllyhz.meapp.ui.feature.add_reminder.AddReminderFragment
import id.rllyhz.meapp.ui.feature.picker.DatePickerFragment
import id.rllyhz.meapp.ui.feature.picker.TimePickerFragment
import java.util.*

class AddItemActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener,
    TimePickerFragment.DialogTimeListener {
    val sharedViewModel: AddItemSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val addItemFragment: Fragment =
            when (intent.getStringExtra(DESTINATION_PAGE) ?: ADDING_NOTES_PAGE) {
                ADDING_NOTES_PAGE -> {
                    AddNoteFragment()
                }
                ADDING_REMINDERS_PAGE -> {
                    AddReminderFragment()
                }
                else -> Fragment()
            }

        supportFragmentManager
            .beginTransaction().add(R.id.main_frame_add_item, addItemFragment)
            .commit()
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
        const val ADDING_REMINDERS_PAGE = "ADDING_REMINDERS_PAGE"
    }
}