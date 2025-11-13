package com.example.practicsing.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Emoji Logo
            Text(
                text = "🎵",
                fontSize = 56.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // App Title
            Text(
                text = "PractiSing",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            //Id text
            Text(
                text = "ID",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, bottom = 4.dp)
            )

            // ID Field
            OutlinedTextField(
                value = userId,
                onValueChange = { userId = it },
                placeholder = { Text("6–12 characters, letters and numbers") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape( 13.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.DarkGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White
                )
            )

            // Password text
            Text(
                text = "Password",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, bottom = 4.dp)
            )

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("8+ characters, including number") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape( 13.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.DarkGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White
                )
            )

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color(0xFFff0088),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Start
                )
            }

            suspend fun checkUserCredentials(userId: String, password: String): Boolean {
                val db = FirebaseFirestore.getInstance()
                val userRef = db.collection("Users").document(userId)

                return try {
                    val snapshot = userRef.get().await()
                    if (snapshot.exists()) {
                        val storedPassword = snapshot.getString("Password")
                        storedPassword == password
                    } else {
                        false
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }
            // Login Button
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        val isValid = checkUserCredentials(userId, password)
                        isLoading = false
                        if (isValid) {
                            errorMessage = null
                            onLoginSuccess()
                        } else {
                            errorMessage = "* Invalid username or password. Please try again."
                        }
                    }
                },
                enabled = userId.isNotBlank() && password.isNotBlank() && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffff0088),
                    disabledContainerColor = Color(0xFF1C1C1E)
                )
            ) {
                Text(if (isLoading) "Checking..." else "Login", color = Color.LightGray)
            }




            Text(
                text = "Click here to sign up",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                fontSize = 17.sp,
                modifier = Modifier
                    .clickable { onSignUpClick() }
                    .padding(top = 6.dp)
            )
        }
    }
}