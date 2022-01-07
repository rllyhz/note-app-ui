package id.rllyhz.meapp.ui.features.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.utils.DataHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel : ViewModel() {
    private var _shouldLoadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _notes: MutableLiveData<List<Note>> = MutableLiveData(DataHelper.getAllNotes())
    private var _selectedNote: MutableLiveData<Note> = MutableLiveData()
    private var _searchQuery: MutableLiveData<String> = MutableLiveData()

    val selectedNote: LiveData<Note> = _selectedNote
    val allNotes: LiveData<List<Note>> = _notes
    val shouldLoadingState: StateFlow<Boolean> = _shouldLoadingState

    fun setSelectedNote(note: Note) {
        _selectedNote.value = note
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearchingNotes() = viewModelScope.launch(Dispatchers.IO) {
        _shouldLoadingState.value = true

        _searchQuery.value?.let { _ ->
            withContext(Dispatchers.Main) {
                _notes.value =
                    DataHelper.getAllNotes().take(4)
            }
        }

        delay(1000)

        _shouldLoadingState.value = false
    }
}