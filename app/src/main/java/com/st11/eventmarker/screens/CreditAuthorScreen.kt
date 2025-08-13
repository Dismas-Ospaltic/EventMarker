package com.st11.eventmarker.screens




import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.eventmarker.R
import com.st11.eventmarker.utils.DynamicStatusBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditAuthorScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.seina)
    DynamicStatusBar(backgroundColor) // ✅ Keeps status bar consistent
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("About & Credits", color = Color.White) },
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
        containerColor = colorResource(id = R.color.light_bg_color)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()) // ✅ Scrollable content
        ) {
            // ✅ About Section
            Text(
                text = "About This App",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Stay on top of your schedule with an easy, powerful tool for creating events, setting reminders, and keeping important details at your fingertips. Stay organized, punctual, and prepared for every occasion.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.4f))

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Credits Section
            Text(
                text = "Credits & Acknowledgements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val iconCredits = listOf(
//
                "App icon was designed by Peerapak Takpho from Flaticon",

                "App Screen image icons:",
                "-No data Placeholder icon image designed by shmai from Flaticon",
                "-No Search data placeholder icon image designed by kerismaker from Flaticon",

            )

            iconCredits.forEach { credit ->
                Text(
                    text = credit,
                    style = if (credit.endsWith(":"))
                        MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    else
                        MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ External link
            Text(
                text = "Visit Flaticon: www.flaticon.com",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .clickable {
                        val url = "https://www.flaticon.com"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                    .padding(vertical = 4.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CreditAuthorScreenPreview() {
    CreditAuthorScreen(navController = rememberNavController())
}