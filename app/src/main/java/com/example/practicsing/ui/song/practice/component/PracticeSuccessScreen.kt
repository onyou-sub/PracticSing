package com.example.practicsing.ui.song.practice.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.practicsing.R
import kotlinx.coroutines.delay
import com.example.practicsing.navigation.Screen

@Composable
fun PracticeSuccessScreen(navController: NavController) {


    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate(Screen.Main.route) {
            popUpTo(Screen.Main.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(100.dp)
            )

            Text("clear song practice!", color = Color.White, fontSize = 24.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "save record at your archive",
                color = Color(0xFFFF66B2),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF0088),
                    contentColor = Color.White
                )
            ) {
                Text("back home")
            }
        }
    }





}

