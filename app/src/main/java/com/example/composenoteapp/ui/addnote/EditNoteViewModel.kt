package com.example.composenoteapp.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenoteapp.model.Note
import com.example.composenoteapp.model.NotesDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    val  db:NotesDatabase
): ViewModel() {

    fun updateNote(note:Note){
        viewModelScope.launch {
            db.dao.updateNote(note)
        }
    }


}
