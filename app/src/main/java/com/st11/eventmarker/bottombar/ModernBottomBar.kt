package com.st11.eventmarker.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.solid.ChartBar
import compose.icons.fontawesomeicons.solid.Cog
import compose.icons.fontawesomeicons.solid.Heart
import compose.icons.fontawesomeicons.solid.MoneyCheck
import compose.icons.fontawesomeicons.solid.Home
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.st11.eventmarker.R
import com.st11.eventmarker.navigation.Screen
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarDay
import java.util.*

@Composable
fun ModernBottomBar(navController: NavHostController) {
    val screens = listOf(Screen.Home, Screen.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        shadowElevation = 8.dp,
        tonalElevation = 6.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = colorResource(id = R.color.bottom_bar_background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            screens.forEach { screen ->
                val isSelected = currentRoute == screen.route
                val selectedColor = colorResource(id = R.color.tab_selected)
                val unselectedColor = colorResource(id = R.color.tab_unselected)

                BottomBarItem(
                    label = screen.route.replaceFirstChar { it.titlecase(Locale.ROOT) },
                    icon = when (screen) {
                        is Screen.Home -> FontAwesomeIcons.Solid.CalendarDay
                        is Screen.Settings -> FontAwesomeIcons.Solid.Cog
                        else -> FontAwesomeIcons.Solid.Home
                    },
                    isSelected = isSelected,
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor
                ) {
                    if (!isSelected) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBarItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit
) {
    val color = if (isSelected) selectedColor else unselectedColor
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier
                .size(22.dp)
                .padding(bottom = 4.dp),
            tint = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}
