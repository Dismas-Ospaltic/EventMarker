package com.st11.eventmarker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.eventmarker.R
import com.st11.eventmarker.navigation.Screen
import com.st11.eventmarker.utils.DynamicStatusBar
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Clipboard
import compose.icons.fontawesomeicons.solid.Plus
import compose.icons.fontawesomeicons.solid.Search
import org.koin.androidx.compose.koinViewModel




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val searchQuery = remember { mutableStateOf("") }
    val backgroundColor = colorResource(id = R.color.seina)

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
                            .padding(horizontal = 16.dp, vertical = 16.dp)
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
                                .weight(0.6f)
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

                        Spacer(modifier = Modifier.width(16.dp))

                        // Create Event Button
                        Button(
                            onClick = { navController.navigate(Screen.AddToCalendar.route) },
                            modifier = Modifier
                                .weight(0.4f)
                                .height(56.dp), // Match TextField height
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.charcoal)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Plus,
                                    contentDescription = "Add icon",
                                    tint = colorResource(id = R.color.white),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "mark calendar",
                                    color = colorResource(id = R.color.white),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

            }
        }

    ) {  paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
//                .padding(
//                    top = paddingValues.calculateTopPadding(),
//                    bottom = 0.dp
//                )
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .background(color = colorResource(id = R.color.light_bg_color))
//                .verticalScroll(rememberScrollState())
        ) {


            val cardData = listOf(
                CardInfo("Number of People/Clients", "300", R.drawable.mark),
                CardInfo("Total Debt", "499", R.drawable.calendar),
                CardInfo("Number of Unpaid", "4774", R.drawable.calendar),
                CardInfo("Number of Paid", "64778", R.drawable.mark),
                CardInfo("Number of Partial Paid Debt", "94", R.drawable.mark),
                CardInfo("Number of Past Due Debts", "377", R.drawable.mark),
                CardInfo("Number of Partial Paid Debt", "94", R.drawable.mark),
                CardInfo("Number of Past Due Debts", "377", R.drawable.mark),
                CardInfo("Number of Partial Paid Debt", "94", R.drawable.mark),
                CardInfo("Number of Past Due Debts", "377", R.drawable.mark),
                CardInfo("Number of Partial Paid Debt", "94", R.drawable.mark),
                CardInfo("Number of Past Due Debts", "377", R.drawable.mark)
            )

            val cardDataReminder = listOf(
            CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
            ,"10:00Am", "12:00pm", "Hospital", "more notes"
            ),
                    CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
                ,"10:00Am", "12:00pm", "Hospital", "more notes"
            ),
            CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
                ,"10:00Am", "12:00pm", "Hospital", "more notes"
            ),
            CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
                ,"10:00Am", "12:00pm", "Hospital", "more notes"
            ),
                CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
                    ,"10:00Am", "12:00pm", "Hospital", "more notes"
                ),
                CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
                    ,"10:00Am", "12:00pm", "Hospital", "more notes"
                ),
                CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
                    ,"10:00Am", "12:00pm", "Hospital", "more notes"
                ),
                CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
                    ,"10:00Am", "12:00pm", "Hospital", "more notes"
                ),
                CardInfoReminder("Doctors appointment", "12/12/2023", "appointment", "high"
                    ,"10:00Am", "12:00pm", "Hospital", "more notes"
                )
            )


            val configuration = LocalConfiguration.current
            val columns = when {
                configuration.screenWidthDp < 400 -> 2
                configuration.screenWidthDp < 600 -> 3
                else -> 4 // More columns for larger screens
            }



//        LazyVerticalGrid(
//            columns = GridCells.Fixed(columns),
//            modifier = Modifier
//                .fillMaxWidth()
//                .heightIn(min = 300.dp) // Give a bounded height to prevent infinite height
//                .padding(horizontal = 12.dp, vertical = 8.dp),
//            userScrollEnabled = true // ❗ disable nested scroll
//        ) {
////            items(cardData) { ( label, count, image) ->
//            items(cardData.size) { index ->
//                val (label, count, image) = cardData[index]
//
//                Card(
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth()
//                        .aspectRatio(1f), // Ensures square shape
//                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
//                    shape = RoundedCornerShape(12.dp), // Slightly more rounded
//                    colors = CardDefaults.cardColors(containerColor = Color.White) // ✅ White background
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Image(
//                            painter = painterResource(id = image),
//                            contentDescription = label,
//                            modifier = Modifier
//                                .size(64.dp)
//                                .padding(bottom = 8.dp)
//                        )
//                        Text(
//                            text = label,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = count.toString(),
//                            fontSize = 12.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                    }
//                }
//            }
//        }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp) // Give a bounded height to prevent infinite height
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                userScrollEnabled = true // ❗ disable nested scroll
            ) {
//            items(cardData) { ( label, count, image) ->
//                items(cardData.size) { index ->
//                    val (label, count, image) = cardData[index]
             items(cardDataReminder.size) { index ->
                 val ( title, date, category, priority, startTime, endTime, venue, extraNote) = cardDataReminder[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .aspectRatio(1f), // Ensures square shape
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                        shape = RoundedCornerShape(12.dp), // Slightly more rounded
                        colors = CardDefaults.cardColors(containerColor = Color.White) // ✅ White background
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Reminder:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.persianGreen)
                            )
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "On $date",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "From $startTime to $endTime",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "On $date",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }




        }
    }
}

data class CardInfo(val title: String, val amount: String, val iconRes: Int)
data class CardInfoReminder(val title: String, val date: String,
                            val category: String, val priority: String,
                           val startTime: String,
                            val endTime: String,
                           val venue: String,
                            val extraNote: String
    )


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}