package com.example.composenoteapp.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenoteapp.R
import com.example.composenoteapp.model.Note
import com.example.composenoteapp.ui.destinations.EditNoteDestination
import com.example.composenoteapp.ui.destinations.MainScreenDestination
import com.example.composenoteapp.ui.destinations.PreviewNoteDestination
import com.example.composenoteapp.ui.noteslist.*
import com.example.composenoteapp.ui.previwnote.PreviewNoteViewModel
import com.example.composenoteapp.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchNotes(
    nav: DestinationsNavigator,
    viewModel: NotesListViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

            val searchText = remember {
                mutableStateOf("")
            }
        Card(
            modifier = Modifier.padding(30.dp, 30.dp),
            shape = RoundedCornerShape(45.dp),
        ) {
            Card(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(45.dp),
                backgroundColor = Black
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp, 0.dp, 10.dp, 0.dp),
                        value = searchText.value,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            disabledTextColor = Black,
                            backgroundColor = Black,
                            focusedIndicatorColor = Black,
                            unfocusedIndicatorColor = Black,
                            disabledIndicatorColor = Black,
                            cursorColor = Color.White
                        ),
                        onValueChange = {
                            searchText.value = it
                            viewModel.getNotes(it)
                        },
                        placeholder = {
                            Text(text = "")
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.close) ,
                                modifier = Modifier.size(24.dp),
                                tint = Color.White,
                                contentDescription = ""
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                nav.navigate(MainScreenDestination)
                            }
                        )
                    )
                }
            }
        }
        NotesList(
            nav = nav
        )
    }
}

