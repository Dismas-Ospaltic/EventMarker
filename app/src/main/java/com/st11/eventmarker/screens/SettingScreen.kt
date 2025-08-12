package com.st11.eventmarker.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.st11.eventmarker.navigation.Screen
import com.st11.eventmarker.screens.components.SettingCard
import com.st11.eventmarker.utils.DynamicStatusBar
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Bell
import compose.icons.fontawesomeicons.solid.Clipboard
import compose.icons.fontawesomeicons.solid.InfoCircle
import compose.icons.fontawesomeicons.solid.Lock
import compose.icons.fontawesomeicons.solid.Search
import org.koin.androidx.compose.koinViewModel




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {

    val searchQuery = remember { mutableStateOf("") }
    val backgroundColor = colorResource(id = R.color.seina)
    val context = LocalContext.current

    DynamicStatusBar(backgroundColor)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) {  paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(
//                    top = paddingValues.calculateTopPadding(),
//                    bottom = 0.dp
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding() + 78.dp
                )
                .background(color = colorResource(id = R.color.light_bg_color))
                .verticalScroll(rememberScrollState()) // ✅ Enable scrolling
        ) {

            var notificationsEnabled by remember { mutableStateOf(true) }
            SettingCard(
                icon = FontAwesomeIcons.Solid.Bell,
                title = "Notifications",
                iconColor = colorResource(id=R.color.charcoal), // Orange
                trailing = {
                    Switch(
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorResource(id = R.color.dark),
                            checkedTrackColor = colorResource(id = R.color.dark).copy(alpha = 0.5f),
                            uncheckedThumbColor = Color.LightGray,
                            uncheckedTrackColor = Color.Gray
                        ),
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                },
                onClick = { notificationsEnabled = !notificationsEnabled }
            )



            SettingCard(
                icon = FontAwesomeIcons.Solid.InfoCircle,
                title = "About",
                iconColor = colorResource(id=R.color.seina), // Green
                onClick = { /* Navigate */
                navController.navigate(Screen.CreditAuthor.route)
                }
            )


            SettingCard(
                icon = FontAwesomeIcons.Solid.Lock,
                title = "Privacy policy",
                iconColor = colorResource(id=R.color.persianGreen), // Green
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://st11-homy.github.io/EventMarker/privacy-policy.html")
                    )
                    context.startActivity(intent)
                /* Navigate */ }
            )




        }
    }
}



@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(navController = rememberNavController())
}