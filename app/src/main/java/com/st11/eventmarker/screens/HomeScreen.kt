package com.st11.eventmarker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.eventmarker.R
import com.st11.eventmarker.navigation.Screen
import com.st11.eventmarker.screens.components.EditablePopup
import com.st11.eventmarker.utils.DynamicStatusBar
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Clipboard
import compose.icons.fontawesomeicons.solid.Plus
import compose.icons.fontawesomeicons.solid.Search
import org.koin.androidx.compose.koinViewModel
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val searchQuery = remember { mutableStateOf("") }
    val backgroundColor = colorResource(id = R.color.seina)

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var selectedNotes by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Initial Text") }


    val configuration = LocalConfiguration.current
    val columns = when {
        configuration.screenWidthDp < 400 -> 2
        configuration.screenWidthDp < 600 -> 3
        else -> 4 // More columns for larger screens
    }

    DynamicStatusBar(backgroundColor)

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            "Reminders",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColor,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 9.dp)
                            .background(colorResource(id = R.color.white)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Search Field
                        TextField(
                            value = searchQuery.value,
                            onValueChange = { searchQuery.value = it },
                            placeholder = { Text(text = "Search...") },
                            leadingIcon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Search,
                                    contentDescription = "Search Icon",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(colorResource(id=R.color.light_bg_color)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                    }

            }
        }

    ) {  paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    //                 paddingValues
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding() + 80.dp
                )
                .background(color = colorResource(id = R.color.light_bg_color))
//                .verticalScroll(rememberScrollState())
        ) {


//

            val cardDataReminder = listOf(
                CardInfoReminder(
                    "Doctors appointment",
                    "12/12/2023",
                    "appointment",
                    "high",
                    "10:00AM",
                    "12:00PM",
                    "Hospital",
                    "Bring all reports and X-ray images."
                ),
                CardInfoReminder(
                    "Gym Session",
                    "14/12/2023",
                    "fitness",
                    "low",
                    "06:00PM",
                    "07:00PM",
                    "City Gym",
                    "Don’t forget water bottle."
                ),
                CardInfoReminder(
                    "Doctors appointment",
                    "12/12/2023",
                    "appointment",
                    "high",
                    "10:00AM",
                    "12:00PM",
                    "Hospital",
                    "Bring all reports and X-ray images."
                )
            )



//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(columns),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(min = 300.dp)
//                            .padding(horizontal = 12.dp, vertical = 8.dp),
//                        userScrollEnabled = false // ✅ Important: Disable nested scroll inside LazyColumn
//                    ) {
//                        items(cardDataReminder.size) { index ->
//                            val (title, date, type, priority, startTime, endTime, venue, extraNote) = cardDataReminder[index]
//                            ReminderCard(
//                                priority = priority,
//                                title = title,
//                                date = date,
//                                time = "$startTime - $endTime",
//                                venue = venue,
//                                onMoreNotesClick = {
//                                    selectedNotes = extraNote
//                                    showSheet = true
//                                }
//                            )
//                        }
//                    }



                   LazyVerticalGrid(
                       columns = GridCells.Fixed(columns),
                       modifier = Modifier
                           .fillMaxWidth() // Important: fill width of its parent (LazyColumn item)
                           .heightIn(min = 300.dp) // Provide a min height if needed, or let content define it
                           .wrapContentHeight() // Allow height to wrap its content
                           .padding(horizontal = 12.dp, vertical = 8.dp),
                       userScrollEnabled = true // ✅ IMPORTANT: Disable scrolling for the inner LazyVerticalGrid
                   ) {
                       item(span = { GridItemSpan(maxLineSpan) }) {
                           // Header 1

                           Text(
                               text = "Upcoming Reminders",
                               color = Color.White,
                               fontSize = 16.sp,
                               fontWeight = FontWeight.Bold,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .background(colorResource(id = R.color.persianGreen))
                                   .padding(vertical = 12.dp, horizontal = 16.dp),
                               textAlign = TextAlign.Center
                           )

                       }

                       items(cardDataReminder.size) { index ->
                           val (title, date, type, priority, startTime, endTime, venue, extraNote) = cardDataReminder[index]
                           ReminderCard(
                               priority = priority,
                               title = title,
                               date = date,
                               time = "$startTime - $endTime",
                               venue = venue,
                               onMoreNotesClick = {
                                   selectedNotes = extraNote
                                   showSheet = true
                               },
                               onEditClick = {
                                   showDialog = true // Just trigger the flag
                               }


                           )
                           if (showDialog) {
                               EditablePopup(
                                   initialText = selectedText,
                                   onDismiss = { showDialog = false },
                                   onSave = { newText ->
                                       selectedText = newText
                                       showDialog = false
                                   }
                               )
                           }
                       }

                       item(span = { GridItemSpan(maxLineSpan) }) {
                           Text(
                               text = "Past Date Reminders",
                               color = Color.White,
                               fontSize = 16.sp,
                               fontWeight = FontWeight.Bold,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .background(colorResource(id = R.color.persianGreen))
                                   .padding(vertical = 12.dp, horizontal = 16.dp),
                               textAlign = TextAlign.Center
                           )
                       }
                       items(cardDataReminder.size) { index ->
                           val (title, date, type, priority, startTime, endTime, venue, extraNote) = cardDataReminder[index]
                           ReminderCard(
                               priority = priority,
                               title = title,
                               date = date,
                               time = "$startTime - $endTime",
                               venue = venue,
                               onMoreNotesClick = {
                                   selectedNotes = extraNote
                                   showSheet = true
                               },
                               onEditClick = {

                               }
                           )
                       }
                   }


        }
    }


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Additional Notes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(selectedNotes)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showSheet = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

data class CardInfoReminder(
    val title: String,
    val date: String,
    val type: String,
    val priority: String,
    val startTime: String,
    val endTime: String,
    val venue: String,
    val moreNotes: String
)


@Composable
fun ReminderCard(
    priority: String, // "high" or "low"
    title: String,
    date: String,
    time: String,
    venue: String,
    onMoreNotesClick: () -> Unit,
    onEditClick: () -> Unit
) {

    var isMenuExpanded by remember { mutableStateOf(false) }
    val cardColor = when (priority.lowercase()) {
        "high" -> Color(0xFFFFE5E5) // Light red
        "medium" -> Color(0xFFFFF8E1) // Light yellow
        else -> Color(0xFFE8F5E9) // Light green
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        isMenuExpanded = true
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Reminder",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00796B) // Persian Green shade
                )
            }
            Text(
                text = "priority: " + priority.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (priority.lowercase() == "high") Color.Red else Color(0xFF00796B)
            )
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(text = "Date: $date", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Time: $time", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Venue: $venue", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onMoreNotesClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text(text = "More Notes", color = Color.White)
            }


            // 👇 Long-press menu
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = {
                        isMenuExpanded = false
                        onEditClick()
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                )
            }
        }
    }
}




