package com.example.practicsing.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.practicsing.navigation.Screen

import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Gray

private val DarkBackground = Color(0xFF000000)
private val FieldBackground = Color(0xFF1C1C1C)

@Composable
fun LoginScreen(navController: NavHostController) {

    var idInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    val attemptLogin = {
        if (idInput.isNotBlank() && passwordInput.length >= 8) {
            loginError = null
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            loginError = "Please check ID and Password."
        }
    }

    Scaffold(
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // 앱 로고
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(PinkAccent, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Logo", color = MainText)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "PracticSing",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MainText
            )

            Spacer(modifier = Modifier.height(64.dp))

            // --- ID ---
            Text("ID", modifier = Modifier.fillMaxWidth(), color = Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = idInput,
                onValueChange = {
                    idInput = it
                    loginError = null
                },
                placeholder = { Text("6-12 characters, letters and numbers") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Password ---
            Text("Password", modifier = Modifier.fillMaxWidth(), color = Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = passwordInput,
                onValueChange = {
                    passwordInput = it
                    loginError = null
                },
                placeholder = { Text("8+ characters, including number") },
                singleLine = true,
                visualTransformation =
                    if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "toggle password",
                            tint = Gray
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 오류 메시지
            AnimatedVisibility(visible = loginError != null) {
                Text(
                    text = loginError ?: "",
                    color = PinkAccent,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 로그인 버튼
            Button(
                onClick = attemptLogin,
                enabled = idInput.isNotBlank() && passwordInput.length >= 8,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Click here to sign up",
                color = Gray,
                modifier = Modifier.clickable { navController.popBackStack() },
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
