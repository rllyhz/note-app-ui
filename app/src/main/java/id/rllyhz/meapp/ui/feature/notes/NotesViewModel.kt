package id.rllyhz.meapp.ui.feature.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.utils.DataHelper

class NotesViewModel : ViewModel() {
    private var _notes: LiveData<List<Note>>

    init {
        _notes = liveData { emit(DataHelper.getAllNotes()) }
    }

    fun reloadAllNotes() {
        val notes = DataHelper.getAllNotes()

        _notes = liveData { emit(notes) }
    }

    fun getAllNotes(): LiveData<List<Note>> = _notes
}