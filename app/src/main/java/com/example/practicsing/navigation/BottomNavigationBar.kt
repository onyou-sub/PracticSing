package com.example.practicsing.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.FormatListNumbered // Practice 아이콘
import androidx.compose.material.icons.filled.MusicVideo // Song 아이콘
import androidx.compose.material.icons.filled.Person // MyPage 아이콘
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
import com.example.practicsing.navigation.BottomNavItem

// ⭐ Screen 클래스 import 추가
import com.example.practicsing.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        // Home 라우트 사용
        BottomNavItem("Home", Icons.Filled.Home, Screen.Home.route),

        // ⭐ Song 라우트 사용
        BottomNavItem("Song", Icons.Filled.MusicVideo, Screen.Song.route),

        // Pract 라우트 사용
        BottomNavItem("Pract", Icons.Filled.FormatListNumbered, Screen.Practice.route),

        // ⭐ MyPage 라우트 사용
        BottomNavItem("My", Icons.Filled.Person, Screen.MyPage.route)
    )

    NavigationBar(containerColor = DarkBackground) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            // 현재 라우트가 item.route와 정확히 일치하는지 확인
            // Note: 현재 route를 매칭할 때, query parameter가 붙을 수 있는 DailyPractice가 아닌
            // Practice 탭 경로와 MyPage 탭 경로에 대해 정확히 매칭되도록 정의했습니다.
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
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
                                    .background(PinkAccent, RoundedCornerShape(2.dp))
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