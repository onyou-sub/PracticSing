package com.example.practicsing.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Person
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, Screen.Home.route),
        BottomNavItem("Song", Icons.Filled.MusicVideo, Screen.Song.route),
        BottomNavItem("Practice", Icons.Filled.FormatListNumbered, Screen.Practice.route),
        BottomNavItem("MyPage", Icons.Filled.Person, Screen.MyPage.route)
    )

    NavigationBar(containerColor = DarkBackground) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .height(1.dp)
                                    .width(25.dp)
                                    .background(PinkAccent)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                        } else {
                            Spacer(modifier = Modifier.height(3.dp))
                        }

                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (selected) PinkAccent else Gray
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selected) PinkAccent else Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = DarkBackground
                )
            )
        }
    }
}
