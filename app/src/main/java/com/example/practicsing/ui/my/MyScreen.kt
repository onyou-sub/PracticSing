package com.example.practicsing.ui.my

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicsing.navigation.Screen
import androidx.core.content.edit

@Composable
fun MyScreen(navController: NavController) {
    val context = LocalContext.current
    var userId by remember { mutableStateOf("") }

    val sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    userId = sharedPref.getString("userid", "").toString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "My Screen")
        Text(text = "Welcome $userId")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            sharedPref.edit {
                remove("userid")
            }
            navController.navigate(Screen.Login.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }) {
            Text("Log Out")
        }
    }
}
