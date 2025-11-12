package com.example.practicsing.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Spacer
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.DarkBackground


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, "home"),
        BottomNavItem("Song", Icons.Filled.MusicVideo, "song"),
        BottomNavItem("Rank", Icons.Filled.FormatListNumbered, "rank"),
        BottomNavItem("My", Icons.Filled.Person, "my")
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
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .height(1.dp)
                                    .width(25.dp)
                                    .background(PinkAccent, RoundedCornerShape(2.dp))
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                        } else {
                            Spacer(modifier = Modifier.height(10.dp))
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