package com.example.practicsing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practicsing.ui.LoginScreen
import com.example.practicsing.ui.RegisterScreen
import com.example.practicsing.ui.theme.PracticSingTheme

class MainActivity : ComponentActivity() {

    enum class Screen {
        HOME, LOGIN, REGISTER
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticSingTheme {
                var currentScreen by remember { mutableStateOf(Screen.HOME) }

                when (currentScreen) {
                    Screen.HOME -> HomeScreen(
                        onLoginClick = { currentScreen = Screen.LOGIN },
                        onRegisterClick = { currentScreen = Screen.REGISTER }
                    )
                    Screen.LOGIN -> LoginScreen(onLoginSuccess = { currentScreen = Screen.HOME })
                    Screen.REGISTER -> RegisterScreen(onRegistrationSuccess = {
                        currentScreen = Screen.HOME
                    })
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Hello World!")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onLoginClick) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onRegisterClick) {
                Text("Register")
            }
        }
    }
}
