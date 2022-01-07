package id.rllyhz.meapp.ui.activities.adding_item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class AddItemSharedViewModel : ViewModel() {
    private var _selectedTime = MutableLiveData<Date>()
    private var _titleText = MutableLiveData<String>()
    private var _contentText = MutableLiveData<String>()
    private var _descriptionText = MutableLiveData<String>()
    private var _isDailyNotification = MutableLiveData<Boolean>()

    val selectedTime: LiveData<Date> get() = _selectedTime
    val titleText: LiveData<String> get() = _titleText
    val contentText: LiveData<String> get() = _contentText
    val descriptionText: LiveData<String> get() = _descriptionText
    val isDailyNotifications: LiveData<Boolean> get() = _isDailyNotification

    fun setSelectedTime(date: Date) {
        _selectedTime.value = date
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
}