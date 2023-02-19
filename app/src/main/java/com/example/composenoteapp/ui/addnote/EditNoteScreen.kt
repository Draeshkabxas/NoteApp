package com.example.composenoteapp.ui.addnote

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenoteapp.ui.destinations.EditNoteDestination
import com.example.composenoteapp.ui.destinations.MainScreenDestination
import com.example.composenoteapp.ui.previwnote.PreviewNoteViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun EditNote(
    nav: DestinationsNavigator,
    viewModel: EditNoteViewModel = hiltViewModel()
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
                        nav.navigate(EditNoteDestination)
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
            Text(text = "Book Review : The Design of Everyday Things by Don Norman",
                modifier = Modifier.padding(0.dp,0.dp,23.dp,0.dp),
                style = TextStyle(
                    fontSize = 35.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.padding(7.dp))
            Text(text = "The Design of Everyday Things is required reading for anyone who is interested in the user experience. I personally like to reread it every year or two.\n" +
                    "\n" +
                    "Norman is aware of the durability of his work and the applicability of his principles to multiple disciplines. \n" +
                    "\n" +
                    "If you know the basics of design better than anyone else, you can apply them flawlessly anywhere.",
                style = TextStyle(
                    fontSize = 23.sp,
                    color = Color.White
                )
            )
        }
    }
}
