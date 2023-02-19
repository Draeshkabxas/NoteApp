package com.example.composenoteapp.ui.previwnote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenoteapp.model.Note
import com.example.composenoteapp.ui.destinations.EditNoteDestination
import com.example.composenoteapp.ui.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PreviewNote(
    nav: DestinationsNavigator,
    viewModel: PreviewNoteViewModel = hiltViewModel(),
    note: Note,
    isNewNote:Boolean
){
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
                    nav.navigate(MainScreenDestination)
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
                       nav.navigate(EditNoteDestination(note,isNewNote))
                    }
                ) {
                    Icon(
                        Icons.Outlined.Edit,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Edit"
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(30.dp,0.dp)
                    .verticalScroll(rememberScrollState())
        ) {
            Text(text = note.title,
                modifier = Modifier.padding(0.dp,0.dp,23.dp,0.dp),
                style = TextStyle(
                    fontSize = 35.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.padding(7.dp))
            Text(text = note.content,
                style = TextStyle(
                    fontSize = 23.sp,
                    color = Color.White
                )
            )
        }
    }
}