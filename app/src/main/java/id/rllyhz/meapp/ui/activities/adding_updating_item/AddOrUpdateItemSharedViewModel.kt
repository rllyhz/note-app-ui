package id.rllyhz.meapp.ui.activities.adding_updating_item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.data.models.Reminder
import java.util.*

class AddOrUpdateItemSharedViewModel : ViewModel() {
    private var _selectedTime = MutableLiveData<Date>()
    private var _selectedDate = MutableLiveData<Date>()
    private var _titleText = MutableLiveData<String>()
    private var _contentText = MutableLiveData<String>()
    private var _descriptionText = MutableLiveData<String>()
    private var _isDailyNotification = MutableLiveData<Boolean>()
    private var _levelOfImportance = MutableLiveData<Int>()
    private var _activeUpdatingPageKey: Int = 0
    private var _activeNote: Note? = null
    private var _activeReminder: Reminder? = null

    val selectedTime: LiveData<Date> get() = _selectedTime
    val selectedDate: LiveData<Date> get() = _selectedDate
    val titleText: LiveData<String> get() = _titleText
    val contentText: LiveData<String> get() = _contentText
    val descriptionText: LiveData<String> get() = _descriptionText
    val isDailyNotifications: LiveData<Boolean> get() = _isDailyNotification
    val levelOfImportance: LiveData<Int> get() = _levelOfImportance

    fun setActivePage(activePage: String) {
        _activeUpdatingPageKey = when (activePage) {
            AddOrUpdateItemActivity.UPDATING_NOTES_PAGE -> 1
            AddOrUpdateItemActivity.UPDATING_REMINDERS_PAGE -> 2
            else -> 0
        }
    }

    fun setDataBeingUpdated(note: Note?) {
        _activeNote = note
    }

    fun setDataBeingUpdated(reminder: Reminder?) {
        _activeReminder = reminder
    }

    fun setSelectedTime(date: Date) {
        _selectedTime.value = date
    }

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
    }

    fun setTitleItem(title: String) {
        _titleText.value = title
    }

    fun setContentItem(content: String) {
        _contentText.value = content
    }

    fun setDescriptionItem(description: String) {
        _descriptionText.value = description
    }

    fun isDailyNotifications(notifyDaily: Boolean) {
        _isDailyNotification.value = notifyDaily
    }

    fun setLevelOfImportance(level: Int) {
        _levelOfImportance.value = level
    }
}