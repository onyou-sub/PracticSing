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
// 어두운 배경과 필드 배경색은 Theme에서 가져오지 않고 임의로 설정합니다.
private val DarkBackground = Color(0xFF000000)
private val FieldBackground = Color(0xFF1C1C1C)

@Composable
fun LoginScreen(navController: NavHostController) {
    // ⚠️ TODO: 실제 View Model과 상태 관리를 연결해야 합니다.
    var idInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    val attemptLogin = {
        if (idInput.isNotBlank() && passwordInput.length >= 8) {
            loginError = null
            // 로그인 성공 시 메인 화면으로 이동
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            loginError = "아이디와 비밀번호를 확인해주세요."
        }
    }

    Scaffold(
        containerColor = DarkBackground // 전체 배경을 검은색으로 설정
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

            // --- 1. ID 입력 필드 ---
            Text("ID", modifier = Modifier.fillMaxWidth(), color = Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = idInput,
                onValueChange = {
                    idInput = it
                    loginError = null
                },
                placeholder = { Text("6-12 characters, letters and numbers", color = Gray.copy(alpha = 0.6f)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PinkAccent,
                    unfocusedBorderColor = FieldBackground,
                    cursorColor = PinkAccent,
                    focusedTextColor = MainText,
                    unfocusedTextColor = MainText,
                    containerColor = FieldBackground
                ),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // --- 2. Password 입력 필드 ---
            Text("Password", modifier = Modifier.fillMaxWidth(), color = Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = passwordInput,
                onValueChange = {
                    passwordInput = it
                    loginError = null
                },
                placeholder = { Text("8+ characters, including number", color = Gray.copy(alpha = 0.6f)) },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility", tint = Gray)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PinkAccent,
                    unfocusedBorderColor = FieldBackground,
                    cursorColor = PinkAccent,
                    focusedTextColor = MainText,
                    unfocusedTextColor = MainText,
                    containerColor = FieldBackground
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // --- 3. 로그인 오류 메시지 ---
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = loginError != null) {
                Text(
                    text = loginError ?: "",
                    color = PinkAccent,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // --- 4. 로그인 버튼 ---
            Button(
                onClick = attemptLogin,
                enabled = idInput.isNotBlank() && passwordInput.length >= 8,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkAccent,
                    disabledContainerColor = FieldBackground.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Login", color = MainText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            // --- 5. 회원가입 링크 ---
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Click here to sign up",
                color = Gray,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                },
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}