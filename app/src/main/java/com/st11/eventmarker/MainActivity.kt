package com.st11.eventmarker


import com.st11.eventmarker.navigation.AppNavHost
import com.st11.eventmarker.navigation.Screen
import android.os.Build
import android.os.Bundle
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.st11.eventmarker.screens.components.HomeFAB
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarDay
import compose.icons.fontawesomeicons.solid.Cog
import compose.icons.fontawesomeicons.solid.Home
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure full-screen layout
        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {
            val navController = rememberAnimatedNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Define screens where the bottom bar should be hidden
            val hideBottomBarScreens = listOf(Screen.AddToCalendar.route)

            Scaffold(
                bottomBar = {
//                    BottomNavigationBar(navController)
                    if (currentRoute !in hideBottomBarScreens) {
                        BottomNavigationBar(navController)
                    }
                },
                floatingActionButton = {
                    if (currentRoute == Screen.Home.route) { // Show FAB only on Home
                        HomeFAB(navController = navController)
                    }
//                    if (currentRoute == Screen.Wishlist.route) { // Show FAB only on Home
//                        WishlistFAB(navController = navController)
//                    }
                }

            ) { paddingValues ->
                AppNavHost(navController,
                    Modifier
                        .padding(paddingValues)
                        .windowInsetsPadding(WindowInsets.systemBars))
            }
        }
    }





}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val screens = listOf(Screen.Home,Screen.Settings)

    val backgroundColor = colorResource(id = R.color.bottom_bar_background)
//    val selectedColor = colorResource(id = R.color.tab_selected)
    val selectedColor = colorResource(id = R.color.seina)
    val unselectedColor = colorResource(id = R.color.tab_unselected)
    val tabIndicatorColor = colorResource(id = R.color.tab_indicator)

    Column{
        // Top Divider
        HorizontalDivider(
            thickness = 1.dp, // Adjust thickness as needed
            color =  colorResource(id = R.color.border_color)
        )
        NavigationBar(
            containerColor = backgroundColor
        ) {
            screens.forEach { screen ->
                val isSelected = currentDestination == screen.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (currentDestination != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {

                        when (screen) {
                            is Screen.Home -> {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.CalendarDay,
                                    contentDescription = "money check",
                                    tint = if (isSelected) selectedColor else unselectedColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }// users icon from font awesome icons
                            is Screen.Settings -> {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Cog,
                                    contentDescription = "setting",
                                    tint = if (isSelected) selectedColor else unselectedColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            else ->  {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Home,
                                    contentDescription = "home",
                                    tint = if (isSelected) selectedColor else unselectedColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }// Home icon from font awesome icons  // Fallback icon
                        }

                    },
                    label = {
                        Text(
                            text = screen.route.replaceFirstChar { it.titlecase(Locale.ROOT) },
                            color = if (isSelected) selectedColor else unselectedColor // Apply custom colors
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        selectedTextColor = selectedColor,
                        unselectedTextColor = unselectedColor,
                        indicatorColor = tabIndicatorColor // Change the background color of selected tab
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = rememberNavController())
}