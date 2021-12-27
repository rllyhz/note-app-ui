package id.rllyhz.meapp.ui.feature.reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.rllyhz.meapp.data.models.Reminder
import id.rllyhz.meapp.utils.DataHelper

class RemindersViewModel : ViewModel() {
    private var _pinnedReminders: LiveData<List<Reminder>> =
        liveData { emit(DataHelper.getAllReminders().takeLast(3)) }
    private var _upcomingReminders: LiveData<List<Reminder>> = liveData { emit(DataHelper.getAllReminders()) }

    fun getAllPinnedReminders(): LiveData<List<Reminder>> = _pinnedReminders

    fun getAllUpcomingReminders(): LiveData<List<Reminder>> = _upcomingReminders
}