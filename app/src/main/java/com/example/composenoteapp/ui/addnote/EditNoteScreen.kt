package com.example.composenoteapp.ui.addnote

import android.util.Log
import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import com.example.composenoteapp.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenoteapp.model.Note
import com.example.composenoteapp.ui.destinations.*
import com.example.composenoteapp.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction


@Destination
@Composable
fun EditNote(
    nav: DestinationsNavigator,
    viewModel: EditNoteViewModel = hiltViewModel(),
    note: Note,
    isNewNote:Boolean
) {
    val back=LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val title = remember {
        mutableStateOf(note.title)
    }

    val body = remember {
        mutableStateOf(note.content)
    }
    val showKeepAlert= remember {
        mutableStateOf(false)
    }
    val showSaveAlert= remember {
        mutableStateOf(false)
    }
    val showColorAlert = remember {
        mutableStateOf(false)
    }

    back?.addCallback { showKeepAlert.value=true }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.padding(30.dp, 30.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(modifier = Modifier
                .size(50.dp),
                shape = RoundedCornerShape(15.dp),
                onClick = {
                    showKeepAlert.value=true
                }
            ) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Back"
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {

                Button(modifier = Modifier
                    .size(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        nav.navigateBack(
                            PreviewNoteDestination(
                                Note(title = title.value, content = body.value, color = note.color),isNewNote
                            )
                        ){
                            back?.addCallback {  }?.remove()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.eye),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Preview"
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Button(modifier = Modifier
                    .size(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        showSaveAlert.value=true
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.save),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Save"
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(30.dp, 0.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            TextFieldWithoutBG(
                text = title,
                textStylePlaceholder = TextStyle(
                    fontSize = 35.sp,
                    color = Color.Gray
                ),
                textStyle = TextStyle(
                    fontSize = 35.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .padding(0.dp, 0.dp, 23.dp, 0.dp)
                    .fillMaxWidth(),
                placeholder = "Title"
            )
            Spacer(modifier = Modifier.padding(7.dp))
            TextFieldWithoutBG(
                text = body,
                textStylePlaceholder = TextStyle(
                    fontSize = 23.sp,
                    color = Color.Gray
                ),
                textStyle = TextStyle(
                    fontSize = 23.sp,
                    color = Color.White
                ),
                placeholder = "Type something..."
            )
        }
    }
    AnimatedVisibility(showSaveAlert.value) {
        showSaveAlert(text = "Save Changes?",yesText="Save", onDismiss = {showSaveAlert.value=false},
            onNo={
            showSaveAlert.value=false
        }) {
            showColorAlert.value=true
        }
    }
    AnimatedVisibility(showKeepAlert.value) {
        showSaveAlert(text = "Are your sure you want discard your changes ?",yesText="Keep", onDismiss = {showKeepAlert.value=false},
            onNo={
                nav.navigateBack(MainScreenDestination){

                    back?.addCallback {  }?.remove()
                }
            }) {
            showKeepAlert.value=false
        }
    }
    AnimatedVisibility(showColorAlert.value) {
        ShowColorAlert(onDismiss = {showColorAlert.value=false}){
                        val savedNote = Note(id = note.id, title = title.value, content = body.value, color = it.value.toLong())
                        if (!isNewNote) {
                            Log.i("Update Note", "Updating")
                            viewModel.updateNote(savedNote)
                        } else {
                            Log.i("Add Note", "Adding")
                            viewModel.addNote(savedNote)
                        }
            nav.navigateBack(MainScreenDestination){

                back?.addCallback {  }?.remove()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowColorAlert(onDismiss: () -> Unit,onClick:(color:Color)->Unit){
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(Icons.Filled.Info, contentDescription = "Save Icon", tint = Color.White)
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Card(
                        backgroundColor = Bink,
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            onClick(Bink)
                        }
                    ) {
                    }
                    Card(
                        backgroundColor = Green,
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            onClick(Green)
                        }
                    ) {
                    }
                    Card(
                        backgroundColor = Yellow,
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            onClick(Yellow)
                        }
                    ) {
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Card(
                        backgroundColor = Blue,
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            onClick(Blue)
                        }
                    ) {
                    }
                    Card(
                        backgroundColor = Purple,
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            onClick(Purple)
                        }
                    ) {
                    }
                    Card(
                        backgroundColor = Red,
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            onClick(Red)
                        }
                    ) {
                    }
                }
            }
        },
        confirmButton = {

        },
        dismissButton = {
        },
    )
}


fun DestinationsNavigator.navigateBack(dir: Direction,onRemove:@Composable ()-> Unit){
    this.navigate(dir)
}

@Composable
fun showSaveAlert(text: String,yesText:String, onDismiss: () -> Unit,onNo:() -> Unit,onYes:()->Unit) {
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(Icons.Filled.Info, contentDescription = "Save Icon", tint = Color.White)
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val textStyle = TextStyle(fontSize = 15.sp)
                Text(text, color = TextGray, style = textStyle)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        modifier = Modifier.size(100.dp,40.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Red),
                        onClick = {  onNo() }) {
                        Text(text = "Discard", color = Color.White)
                    }
                    Button(
                        modifier = Modifier.size(100.dp,40.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Green),
                        onClick = { onYes() }) {
                        Text(text = yesText, color = Color.White)
                    }
                }
            }
        },
        confirmButton = {

        },
        dismissButton = {
        },
    )
}


@Composable
fun TextFieldWithoutBG(
    text: MutableState<String>,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    textStylePlaceholder: TextStyle,
    placeholder: String
) {
    TextField(value = text.value,
        modifier = modifier,
        textStyle = textStyle,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            text.value = it
        },
        placeholder = {
            Text(
                text = placeholder,
                style = textStylePlaceholder,
            )
        }
    )
}
