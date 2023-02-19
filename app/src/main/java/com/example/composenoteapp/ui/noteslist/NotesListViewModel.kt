package com.example.composenoteapp.ui.noteslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenoteapp.model.Note
import com.example.composenoteapp.model.NotesDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesListViewModel @Inject constructor(
    val  db: NotesDatabase
): ViewModel() {


    var notes by mutableStateOf(emptyList<Note>())
        private set

    init {
        getNotes()
    }

    fun getNotes(){
        db.dao.getNotes().onEach { notesList->
            notes =notesList
        }.launchIn(viewModelScope)
    }

    fun addNote(note: Note){
        viewModelScope.launch {
            db.dao.insertNote(note)
        }
    }
    fun deleteNote(note: Note){
        viewModelScope.launch {
            db.dao.deleteNote(note)
        }
    }
}
