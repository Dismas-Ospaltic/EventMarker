package com.st11.eventmarker.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.eventmarker.R
import com.st11.eventmarker.model.EventEntity
import com.st11.eventmarker.utils.DatePickerField
import com.st11.eventmarker.utils.DynamicStatusBar
import com.st11.eventmarker.utils.TimePickerField
import com.st11.eventmarker.viewmodel.EventViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Clipboard
import compose.icons.fontawesomeicons.solid.Search
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMarkScreen(navController: NavController) {


    val backgroundColor = colorResource(id = R.color.seina)
    var eventTitle by remember { mutableStateOf("") }
    var eventVenue by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("") }
    var expanded01 by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    val context = LocalContext.current

    val eventViewModel: EventViewModel = koinViewModel()

    DynamicStatusBar(backgroundColor)


    val priorityType = listOf(
        "high", "low"
    )


    val categoryType = listOf(
        "other",
        "Doctor's Appointment",
        "Gym Session",
        "Yoga Class",
        "Meeting",
        "Work Deadline",
        "Project Review",
        "Conference",
        "Team Standup",
        "Training Session",
        "Workshop",
        "Interview",
        "Personal Errand",
        "Family Event",
        "Birthday",
        "Anniversary",
        "Social Gathering",
        "Dinner Plan",
        "Vacation",
        "Travel",
        "Flight Schedule",
        "Hotel Check-in",
        "School Event",
        "Parent-Teacher Meeting",
        "Exam",
        "Class",
        "Webinar",
        "Religious Event",
        "Prayer Time",
        "Medication Reminder",
        "Bill Payment",
        "Subscription Renewal",
        "Shopping",
        "Car Service",
        "Pet Care",
        "Cleaning Schedule",
        "House Maintenance",
        "Fitness Challenge",
        "Sports Practice",
        "Game Night",
        "Concert",
        "Movie Night",
        "Networking Event"
    )


    Scaffold(

                topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Manage Reminders", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
    ) {  paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                )
                .background(color = colorResource(id = R.color.light_bg_color))
               .verticalScroll(rememberScrollState())
        ) {

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current) + 15.dp,
                        end = paddingValues.calculateEndPadding(LocalLayoutDirection.current) + 15.dp,
                        bottom = paddingValues.calculateBottomPadding() + 25.dp
                    )
                  ,
                verticalArrangement = Arrangement.spacedBy(12.dp) // sets 12dp vertical space between items
            ) {
            OutlinedTextField(
                value = eventTitle,
                onValueChange = { eventTitle = it },
                label = { Text("Event Title *") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                    focusedContainerColor = Color.White.copy(alpha = 0.95f),
                    focusedBorderColor = backgroundColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = backgroundColor,
                    cursorColor = backgroundColor
                ),
                singleLine = true,
            )


                OutlinedTextField(
                    value = eventVenue,
                    onValueChange = { eventVenue = it },
                    label = { Text("Venue e.g medina hall") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = backgroundColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = backgroundColor,
                        cursorColor = backgroundColor
                    ),
                    singleLine = true,
                )



            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = priority,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Event Priority *") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = backgroundColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = backgroundColor,
                        cursorColor = backgroundColor
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(Color.White) // ✅ White background for the dropdown menu
                ) {
                    priorityType.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, color = Color.Black) }, // ✅ Black text
                            onClick = {
                                priority = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }


            ExposedDropdownMenuBox(
                expanded = expanded01,
                onExpandedChange = { expanded01 = !expanded01 }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded01)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = backgroundColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = backgroundColor,
                        cursorColor = backgroundColor
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded01,
                    onDismissRequest = { expanded01 = false },
                    modifier = Modifier
                        .background(Color.White) // ✅ White background for the dropdown menu
                ) {
                    categoryType.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, color = Color.Black) }, // ✅ Black text
                            onClick = {
                                category = selectionOption
                                expanded01 = false
                            }
                        )
                    }
                }
            }


            // ✅ Date Picker
            DatePickerField(label = "Select Date *") { selected ->
                date = selected
            }

            // ✅ Start Time Picker
            TimePickerField(label = "Start Time *") { selected ->
                startTime = selected
            }

            // ✅ End Time Picker
            TimePickerField(label = "End Time *") { selected ->
                endTime = selected
            }


                OutlinedTextField(
                    value = eventDescription,
                    onValueChange = { eventDescription = it },
                    label = { Text("short Notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 200.dp) // Adjust height for ~4 lines
                        .verticalScroll(rememberScrollState()),

                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = backgroundColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = backgroundColor,
                        cursorColor = backgroundColor
                    ),
                    singleLine = false,
                    maxLines = 4
                )


            // ✅ Save Button
            Button(
                onClick = {
                    if (eventTitle.isNotEmpty() && date.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty() && priority.isNotEmpty() && category.isNotEmpty()) {

                    eventViewModel.insertEvent(
                        EventEntity(
                        eventDate = date,
                         eventStartTime = startTime,
                        eventEndTime = endTime,
                        eventTitle = eventTitle,
                        eventVenue = eventVenue,
                        eventPriority = priority,
                         eventId = generateSixDigitRandomNumber().toString(),
                            eventCategory = category
                        )
                    )


                    } else {
                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.persianGreen))
            ) {
                Text("Add to calender", color = Color.White, fontSize = 16.sp)
            }
        }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddMarkScreenPreview() {
    AddMarkScreen(navController = rememberNavController())
}

fun generateSixDigitRandomNumber(): Int {
    return Random.nextInt(1000000, 100000000)  // Generates a random number between 100000 (inclusive) and 1000000 (exclusive)
}