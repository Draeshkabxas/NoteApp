package com.example.composenoteapp.ui.noteslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import com.example.composenoteapp.model.Note
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenoteapp.R
import com.example.composenoteapp.ui.destinations.EditNoteDestination
import com.example.composenoteapp.ui.destinations.MainScreenDestination
import com.example.composenoteapp.ui.destinations.PreviewNoteDestination
import com.example.composenoteapp.ui.destinations.SearchNotesDestination
import com.example.composenoteapp.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun NotesList(
    nav: DestinationsNavigator,
    viewModel: NotesListViewModel = hiltViewModel(),
    showSearchEmpty:Boolean=false
) {
    val list = listOf(
        Bink,
        Green,
        Yellow,
        Blue,
        Purple
    )

    Box() {
        if (viewModel.notes.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val emptyText= remember {
                    mutableStateOf("")
                }
                val emptyImage= remember {
                    mutableStateOf(0)
                }
                if(showSearchEmpty){
                    emptyText.value="File not found. Try searching again."
                    emptyImage.value = R.drawable.cuate
                }else{
                    emptyText.value="Create your first note!"
                    emptyImage.value = R.drawable.rafiki
                }
                Image(
                    modifier = Modifier.size(350.dp, 286.dp),
                    painter = painterResource(id = emptyImage.value),
                    contentDescription = "Empty Note Image"
                )
                Text(
                    text = emptyText.value,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    viewModel.notes
                ) { index: Int, note: Note ->
                    var isDelete= remember {
                        mutableStateOf(false)
                    }
                    AnimatedVisibility(isDelete.value){
                        NoteCard(
                            note = note,
                            color = Red,
                            onCardClick = {
                                viewModel.deleteNote(note)
                                isDelete.value = false
                            },
                            onCardLongClick = {}
                        ){
                            Icon(
                                Icons.Filled.Delete,
                                modifier = Modifier
                                    .padding(20.dp)
                                    .size(49.dp),
                                tint = Color.White,
                                contentDescription = "Delete Note"
                            )
                        }
                    }
                    AnimatedVisibility(!isDelete.value) {
                        NoteCard(
                            note = note,
                            color = list[index % 5],
                            onCardClick = {
                                nav.navigate(PreviewNoteDestination(note=note,false))
                            },
                            onCardLongClick = {
                                isDelete.value = true
                            }
                        ){
                            Text(
                                text = note.title,
                                modifier = Modifier.padding(30.dp, 20.dp),
                                style = TextStyle(
                                    fontSize = 25.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                      nav.navigate(EditNoteDestination(note= Note(title = "", content = "", color = Green.value.toLong()),true))
            },
            backgroundColor = Gray,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(50.dp),
                    ambientColor = Gray,
                    spotColor = Gray
                )
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add FAB",
                tint = Color.White,
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note, color: Color,
    onCardClick: (note: Note) -> Unit,
    onCardLongClick: (note: Note) -> Unit,
     content:@Composable ()->Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    // Regular
                    onCardClick(note)
                },
                onLongClick = {
                    // Long
                    onCardLongClick(note)
                }
            )
            .fillMaxWidth()
            .padding(24.dp, 12.dp),
        backgroundColor = if(color== Red)Red else Color(note.color.toULong())
    ) {
        content()
    }
}


@Destination(start = true)
@Composable
fun MainScreen(
    nav:DestinationsNavigator,
    viewModel: NotesListViewModel= hiltViewModel()
) {
    val isSearch = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                isSearch.value = false
            }
    ) {
        val openDialog = remember { mutableStateOf(false)  }

        AnimatedVisibility(isSearch.value) {
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
                                textColor = TextGray,
                                disabledTextColor = Black,
                                backgroundColor = Black,
                                focusedIndicatorColor = Black,
                                unfocusedIndicatorColor = Black,
                                disabledIndicatorColor = Black,
                                cursorColor = TextGray
                            ),
                            onValueChange = {
                                searchText.value = it
                                viewModel.getNotes(it)
                            },
                            placeholder = {
                                Text(text = "Search by the keyword...",
                                color = TextGray)
                            },
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.close) ,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            searchText.value = ""
                                            viewModel.getNotes()
                                        },
                                    tint = Color.White,
                                    contentDescription = ""
                                )
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    isSearch.value=false
                                }
                            )
                        )
                    }
                }
            }
        }
        AnimatedVisibility(!isSearch.value) {
            Row(
                modifier = Modifier.padding(30.dp, 30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Notes",
                    style = TextStyle(
                        fontSize = 42.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(modifier = Modifier
                        .size(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                           isSearch.value=true
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Search,
                            modifier = Modifier.size(24.dp),
                            contentDescription = "Search"
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(modifier = Modifier
                        .size(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                            openDialog.value = true
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            modifier = Modifier.size(24.dp),
                            contentDescription = "Search"
                        )
                    }
                }
            }
        }
        AnimatedVisibility(openDialog.value){
            ShowInfoAlert {
                openDialog.value=false
            }
        }
        NotesList(
            nav = nav,
            showSearchEmpty = isSearch.value
        )
    }
}


@Composable
fun ShowInfoAlert(onDismiss:()->Unit){
    AlertDialog(
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Black,
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
            onDismiss()
        },
        title = {
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val textStyle = TextStyle(fontSize = 15.sp)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Designed by -  ", color = TextGray, style = textStyle)
                    Text("Redesigned by - ", color = TextGray, style = textStyle)
                    Text("Illustrations -", color = TextGray, style = textStyle)
                    Text("Icons -", color = TextGray, style = textStyle)
                    Text("Font -", color = TextGray, style = textStyle)
                }
                Text("Made by", color = TextGray, style = textStyle)
            }
        },
        confirmButton = {
        },
        dismissButton = {
        }
    )
}
