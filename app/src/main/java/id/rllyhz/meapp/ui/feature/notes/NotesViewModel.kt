package id.rllyhz.meapp.ui.feature.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.utils.DataHelper

class NotesViewModel : ViewModel() {
    private var _notes: LiveData<List<Note>> = liveData { emit(DataHelper.getAllNotes()) }
    private var _selectedNote: MutableLiveData<Note> = MutableLiveData()

    fun getAllNotes(): LiveData<List<Note>> = _notes

    fun setSelectedNote(note: Note) {
        _selectedNote.value = note
    }

    val selectedNote: LiveData<Note?> = _selectedNote
}