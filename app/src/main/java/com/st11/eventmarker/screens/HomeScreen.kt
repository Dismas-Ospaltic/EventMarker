package com.st11.eventmarker.screens

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.st11.eventmarker.model.EventEntity
import com.st11.eventmarker.navigation.Screen
import com.st11.eventmarker.screens.components.EditablePopup
import com.st11.eventmarker.utils.DynamicStatusBar
import com.st11.eventmarker.utils.requestNotificationPermission
import com.st11.eventmarker.viewmodel.EventViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarDay
import compose.icons.fontawesomeicons.solid.Clipboard
import compose.icons.fontawesomeicons.solid.Clock
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
    var selectedEventId by remember { mutableStateOf<String?>(null) }
        var selectedTitle by remember { mutableStateOf<String?>(null) }
    var selectedVenue by remember { mutableStateOf<String?>(null) }
    var selectedEventDescription by remember { mutableStateOf<String?>(null) }
    var selectedPriority by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedSelectedDate by remember { mutableStateOf<String?>(null) }
    var selectedStartTime by remember { mutableStateOf<String?>(null) }
    var selectedEndTime by remember { mutableStateOf<String?>(null) }

    var showDialog by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Initial Text") }


    val configuration = LocalConfiguration.current
    val columns = when {
        configuration.screenWidthDp < 400 -> 1
        configuration.screenWidthDp < 600 -> 2
        else -> 3 // More columns for larger screens
    }

    val context = LocalContext.current
    val activity = context as? Activity



    val eventViewModel: EventViewModel = koinViewModel()
    val events by eventViewModel.events.collectAsState()
    val pastEvents by eventViewModel.pastEvents.collectAsState()

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (activity != null) {
                requestNotificationPermission(activity)
            }
        }


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
                    bottom = paddingValues.calculateBottomPadding() + 78.dp
                )
                .background(color = colorResource(id = R.color.light_bg_color))
//                .verticalScroll(rememberScrollState())
        ) {

                   LazyVerticalGrid(
                       columns = GridCells.Fixed(columns),
                       modifier = Modifier
                           .fillMaxWidth() // Important: fill width of its parent (LazyColumn item)
                           .heightIn(min = 300.dp) // Provide a min height if needed, or let content define it
                           .wrapContentHeight() // Allow height to wrap its content
                           .padding(horizontal = 12.dp, vertical = 8.dp),
                       userScrollEnabled = true // ✅ IMPORTANT: Disable scrolling for the inner LazyVerticalGrid
                   ) {

                       // ✅ **Filter the list based on search query**
                       val filteredEvent = events.filter {
                           it.eventTitle.contains(searchQuery.value, ignoreCase = true) ||
                            it.eventCategory.contains(searchQuery.value, ignoreCase = true)
                       }


                       // ✅ **Filter the list based on search query**
                       val filteredPastEvent = pastEvents.filter {
                           it.eventTitle.contains(searchQuery.value, ignoreCase = true) ||
                                   it.eventCategory.contains(searchQuery.value, ignoreCase = true)
                       }


                       item(span = { GridItemSpan(maxLineSpan) }) {

                           // Header 1

                           Text(
                               text = "Upcoming Reminders",
                               color = Color.White,
                               fontSize = 16.sp,
                               fontWeight = FontWeight.Bold,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .clip(RoundedCornerShape(8.dp)) // ✅ Clip first
                                   .background(colorResource(id = R.color.saffron))
                                   .padding(vertical = 12.dp, horizontal = 16.dp),
                               textAlign = TextAlign.Center
                           )

                       }

                       if (events.isEmpty()) {
                           item(span = { GridItemSpan(maxLineSpan) }) {

                               Box(
                                   modifier = Modifier
                                       .fillMaxSize(),
                                   contentAlignment = Alignment.Center
                               ) {
                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally
                                   ) {
                                       Image(
                                           painter = painterResource(id = R.drawable.work_order), // Replace with your image in res/drawable
                                           contentDescription = "No Data",
                                           modifier = Modifier.size(120.dp)
                                       )
                                       Spacer(modifier = Modifier.height(12.dp))
                                       Text(
                                           text = "No Activities Added, Click the plus (+) Icon to add a Reminder",
                                           color = Color.Gray,
                                           style = MaterialTheme.typography.bodyMedium
                                       )
                                   }
                               }


                           }
                       }else if(filteredEvent.isEmpty()){
 item(span = { GridItemSpan(maxLineSpan) }){
     // No data available after search
     Box(
         modifier = Modifier
             .fillMaxSize(),
         contentAlignment = Alignment.Center
     ) {
         Column(
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             Image(
                 painter = painterResource(id = R.drawable.search), // Replace with your image in res/drawable
                 contentDescription = "No Data",
                 modifier = Modifier.size(120.dp)
             )
             Spacer(modifier = Modifier.height(12.dp))
             Text(
                 text = "No Activities Results Found !",
                 color = Color.Gray,
                 style = MaterialTheme.typography.bodyMedium
             )
         }
     }
 }
                       }else{
                           items(filteredEvent.size){
                               val(id,eventDate, eventStartTime, eventEndTime, eventTitle, eventVenue, eventPriority, eventCategory, noteDescription, eventId, timestamp) = filteredEvent[it]
                               ReminderCard(
                               priority = eventPriority.toString(),
                               title = eventTitle.toString(),
                               date = eventDate,
                               time = "$eventStartTime - $eventEndTime",
                                   venue = if (eventVenue.isNullOrBlank()) "no venue added" else eventVenue,
                               onMoreNotesClick = {
                                   selectedNotes = if (noteDescription.isNullOrBlank()) "no notes Added" else noteDescription
                                   showSheet = true
                               },

                               onEditClick = {
                                   showDialog = true // Just trigger the flag
                                   // 🔹 Store the ID of the selected event
                                   selectedEventId = eventId
                                   selectedTitle = eventTitle
                                   selectedVenue = eventVenue
                                   selectedEventDescription = noteDescription
                                   selectedPriority = eventPriority
                                   selectedCategory = eventCategory
                                   selectedSelectedDate = eventDate
                                   selectedStartTime = eventStartTime
                                   selectedEndTime = eventEndTime

                                   // 🔹 Load event data from ViewModel before showing popup
                                   eventViewModel.loadEventById(eventId)

                               }


                           )
                               if (showDialog && selectedEventId != null) {
                                   EditablePopup(
//                                       initialText = selectedText,
                                       onDismiss = { showDialog = false },
//                                       onSave = { newText ->
//                                           selectedText = newText
//                                           showDialog = false
//                                       },
                                       itemId = selectedEventId!!,
                                       eventDate = selectedSelectedDate!!,
                                       eventStartTime = selectedStartTime!!,
                                       eventEndTime = selectedEndTime!!,
                                       eventTitle = selectedTitle!!,
                                       eventVenue = selectedVenue!!,
                                       eventPriority = selectedPriority!!,
                                       eventCategory = selectedCategory!!,
                                       noteDescription = selectedEventDescription!!
                                   )
                               }

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
                                   .clip(RoundedCornerShape(8.dp)) // ✅ Clip first
                                   .background(colorResource(id = R.color.charcoal))
                                   .padding(vertical = 12.dp, horizontal = 16.dp),
                               textAlign = TextAlign.Center
                           )
                       }


                       if(pastEvents.isEmpty()) {

                           item(span = { GridItemSpan(maxLineSpan) }) {

                               Box(
                                   modifier = Modifier
                                       .fillMaxSize(),
                                   contentAlignment = Alignment.Center
                               ) {
                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally
                                   ) {
                                       Image(
                                           painter = painterResource(id = R.drawable.work_order), // Replace with your image in res/drawable
                                           contentDescription = "No Data",
                                           modifier = Modifier.size(120.dp)
                                       )
                                       Spacer(modifier = Modifier.height(12.dp))
                                       Text(
                                           text = "No data available!",
                                           color = Color.Gray,
                                           style = MaterialTheme.typography.bodyMedium
                                       )
                                   }
                               }


                           }
                       }else if(filteredPastEvent.isEmpty()){
                               item(span = { GridItemSpan(maxLineSpan) }){
                                   // No data available after search
                                   Box(
                                       modifier = Modifier
                                           .fillMaxSize(),
                                       contentAlignment = Alignment.Center
                                   ) {
                                       Column(
                                           horizontalAlignment = Alignment.CenterHorizontally
                                       ) {
                                           Image(
                                               painter = painterResource(id = R.drawable.search), // Replace with your image in res/drawable
                                               contentDescription = "No Data",
                                               modifier = Modifier.size(120.dp)
                                           )
                                           Spacer(modifier = Modifier.height(12.dp))
                                           Text(
                                               text = "No Activities Results Found !",
                                               color = Color.Gray,
                                               style = MaterialTheme.typography.bodyMedium
                                           )
                                       }
                                   }

                               }



                       }else{
                           items(filteredPastEvent.size) {
                               val (id, eventDate, eventStartTime, eventEndTime, eventTitle, eventVenue, eventPriority, eventCategory, noteDescription, eventId, timestamp) = filteredPastEvent[it]

                               PastReminderCard(
                                   priority = eventPriority.toString(),
                                   title = eventTitle.toString(),
                                   date = eventDate,
                                   time = "$eventStartTime - $eventEndTime",
                                   venue = if (eventVenue.isNullOrBlank()) "no venue added" else eventVenue,
                                   onMoreNotesClick = {
//                                       selectedNotes = noteDescription.ifBlank { "no notes Added" }

                                       selectedNotes =
                                           if (noteDescription.isNullOrEmpty()) "no notes" else noteDescription
                                       showSheet = true
                                   }
                               )
                           }
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
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
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


@OptIn(ExperimentalMaterial3Api::class)
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
    val interactionSource = remember { MutableInteractionSource() }
    var isMenuExpanded by remember { mutableStateOf(false) }

    val gradientColors = when (priority.lowercase()) {
        "high" -> listOf(Color(0xFFFFCDD2), Color(0xFFFFEBEE)) // Red gradient
        "low" -> listOf(Color(0xFFFFF9C4), Color(0xFFFFFDE7)) // Yellow gradient
        else -> listOf(Color(0xFFC8E6C9), Color(0xFFE8F5E9)) // Green gradient
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(interactionSource = interactionSource, indication = null) {}
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { isMenuExpanded = true }
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(gradientColors)
                )
                .padding(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reminder",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF004D40)
                    )
                }

                // Priority
                Text(
                    text = "Priority: " + priority.replaceFirstChar { it.uppercase() },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (priority.lowercase() == "high") Color.Red else Color(0xFF388E3C)
                )

                // Title
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                // Details with icons
                DetailRow(icon = Icons.Default.DateRange, label = date)
                DetailRow(icon = FontAwesomeIcons.Solid.Clock, label = time)
                DetailRow(icon = Icons.Default.Place, label = venue)

                Spacer(modifier = Modifier.height(12.dp))

                // More Notes Button (modern style)
                OutlinedButton(
                    onClick = onMoreNotesClick,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF00796B)),
                    border = BorderStroke(1.dp, Color(0xFF00796B))
                ) {
                    Text(text = "More Notes", fontWeight = FontWeight.Medium)
                }

                // Long-press menu
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
}

@Composable
fun DetailRow(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, fontSize = 14.sp, color = Color.DarkGray)
    }
}


//@Composable
//fun ReminderCard(
//    priority: String, // "high" or "low"
//    title: String,
//    date: String,
//    time: String,
//    venue: String,
//    onMoreNotesClick: () -> Unit,
//    onEditClick: () -> Unit
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//    val isPressed by interactionSource.collectIsPressedAsState()
//
//    var isMenuExpanded by remember { mutableStateOf(false) }
//    val cardColor = when (priority.lowercase()) {
//        "high" -> Color(0xFFFFE5E5) // Light red
//        "low" -> Color(0xFFFFF8E1) // Light yellow
//        else -> Color(0xFFE8F5E9) // Light green
//    }
//
//    Card(
//        modifier = Modifier
//            .padding(12.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(16.dp))
//            .shadow(8.dp, RoundedCornerShape(16.dp))
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onLongPress = {
//                        isMenuExpanded = true
//                    }
//                )
//            }
//            .then(
//                if (isPressed) Modifier.background(cardColor.copy(alpha = 0.8f))
//                else Modifier.background(cardColor)
//            ),
//        colors = CardDefaults.cardColors(containerColor = cardColor),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Reminder",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF00796B) // Persian Green shade
//                )
//            }
//            Text(
//                text = "priority: " + priority.replaceFirstChar {
//                    if (it.isLowerCase()) it.titlecase(
//                        Locale.getDefault()
//                    ) else it.toString()
//                },
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Bold,
//                color = if (priority.lowercase() == "high") Color.Red else Color(0xFF00796B)
//            )
//            Text(
//                text = title,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.Black
//            )
//            Text(text = "Date: $date", fontSize = 14.sp, color = Color.Gray)
//            Text(text = "Time: $time", fontSize = 14.sp, color = Color.Gray)
//            Text(text = "Venue: $venue", fontSize = 14.sp, color = Color.Gray)
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Button(
//                onClick = onMoreNotesClick,
//                modifier = Modifier.align(Alignment.End),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
//            ) {
//                Text(text = "More Notes", color = Color.White)
//            }
//
//
//            // 👇 Long-press menu
//            DropdownMenu(
//                expanded = isMenuExpanded,
//                onDismissRequest = { isMenuExpanded = false }
//            ) {
//                DropdownMenuItem(
//                    text = { Text("Edit") },
//                    onClick = {
//                        isMenuExpanded = false
//                        onEditClick()
//                    },
//                    leadingIcon = {
//                        Icon(Icons.Default.Edit, contentDescription = "Edit")
//                    }
//                )
//            }
//        }
//    }
//}

////past date reminder cards
//@Composable
//fun PastReminderCard(
//    priority: String, // "high" or "low"
//    title: String,
//    date: String,
//    time: String,
//    venue: String,
//    onMoreNotesClick: () -> Unit
//) {
//
//
//    Card(
//        modifier = Modifier
//            .padding(12.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(16.dp))
//            .shadow(8.dp, RoundedCornerShape(16.dp)),
//        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.gray01)),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Reminder",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF00796B) // Persian Green shade
//                )
//            }
//            Text(
//                text = "priority: " + priority.replaceFirstChar {
//                    if (it.isLowerCase()) it.titlecase(
//                        Locale.getDefault()
//                    ) else it.toString()
//                },
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Bold,
//                color = if (priority.lowercase() == "high") Color.Red else Color(0xFF00796B)
//            )
//            Text(
//                text = title,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.Black
//            )
//            Text(text = "Date: $date", fontSize = 14.sp, color = Color.Gray)
//            Text(text = "Time: $time", fontSize = 14.sp, color = Color.Gray)
//            Text(text = "Venue: $venue", fontSize = 14.sp, color = Color.Gray)
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Button(
//                onClick = onMoreNotesClick,
//                modifier = Modifier.align(Alignment.End),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
//            ) {
//                Text(text = "More Notes", color = Color.White)
//            }
//        }
//    }
//}
//
//
//

@Composable
fun PastReminderCard(
    priority: String, // "high" or "low"
    title: String,
    date: String,
    time: String,
    venue: String,
    onMoreNotesClick: () -> Unit
) {
    val priorityColor = when (priority.lowercase()) {
        "high" -> Color(0xFFE57373) // Muted red
        "low" -> Color(0xFFFFF59D) // Muted yellow
        else -> Color(0xFFA5D6A7) // Muted green
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .alpha(0.7f),
        // Faded look
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Past Reminder",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                Text(
                    text = "Expired",
                    fontSize = 12.sp,
                    color = Color.Red.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Priority: " + priority.replaceFirstChar { it.uppercase() },
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = priorityColor.copy(alpha = 0.6f)
            )

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            PastDetailRow(Icons.Default.DateRange, date)
            PastDetailRow(FontAwesomeIcons.Solid.Clock, time)
            PastDetailRow(Icons.Default.Place, venue)

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onMoreNotesClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f))
            ) {
                Text(text = "View Notes", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun PastDetailRow(icon: ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, fontSize = 13.sp, color = Color.Gray)
    }
}

