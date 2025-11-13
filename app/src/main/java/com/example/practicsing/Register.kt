package com.example.practicsing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.*

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.example.practicsing.TopBar


enum class RegistrationStep {
    STEP_1, // ID and Password
    STEP_2  // Name, Gender, Favorite Singer
}

suspend fun registerUser(
    userId: String,
    password: String,
    name: String,
    gender: String,
    favoriteSinger: String
): Boolean {
    return try {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("Users").document(userId)
        val snapshot = userRef.get().await()
        if (snapshot.exists()) {
            false
        } else {
            val userData = hashMapOf(
                "userId" to userId,
                "Password" to password,
                "Name" to name,
                "Gender" to gender,
                "FavoriteSinger" to favoriteSinger
            )
            userRef.set(userData).await()
            true
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegistrationSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var favoriteSinger by remember { mutableStateOf("") }
    var errorMessage1 by remember { mutableStateOf<String?>(null) }
    var errorMessage2 by remember { mutableStateOf<String?>(null) }
    var errorMessage3 by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(RegistrationStep.STEP_1) }
    val scope = rememberCoroutineScope()
    val isNameValid = name.trim().length >= 2
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Scaffold(
            containerColor = Color.Black,
            topBar = {
                TopBar(
                    title = if (currentStep == RegistrationStep.STEP_1) "Sign up" else "Sign up",
                    currentStep = currentStep.ordinal + 1, // +1 to display 1-based index
                    totalSteps = RegistrationStep.entries.size
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp, vertical = 40.dp),
                horizontalAlignment = Alignment.Start
            ) {
                when (currentStep) {


                    RegistrationStep.STEP_1 -> {
                        // Step 1: ID and Password
                        Text(
                            text = "Hello!\nRegister to get started.",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFFECECEE)
                            )
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        // ID Field
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color(0xFFa6a7ab))) {
                                    append("ID")
                                }
                                withStyle(style = SpanStyle(color = Color(0xFFFF0088))) {
                                    append(" *")
                                }
                            },
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = userId,
                            onValueChange = { userId = it },
                            placeholder = {
                                Text("6–12 characters, letters and numbers", fontSize = 14.sp)
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(13.dp),
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
                        errorMessage1?.let {
                            Text(
                                text = it,
                                color = Color(0xFFff0088),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        // Password Field
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color(0xFFa6a7ab))) {
                                    append("Password")
                                }
                                withStyle(style = SpanStyle(color = Color(0xFFFF0088))) {
                                    append(" *")
                                }
                            },
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = {
                                Text(
                                    text = "8+ characters, including number & special char",
                                    fontSize = 14.sp
                                )
                            },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(13.dp),
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
                        errorMessage2?.let {
                            Text(
                                text = it,
                                color = Color(0xFFff0088),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        // Registration error
                        errorMessage3?.let {
                            Text(
                                text = it,
                                color = Color(0xFFff0088),
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        // Next Button
                        Button(
                            onClick = {
                                scope.launch {
                                    isLoading = true
                                    // Only validate ID uniqueness, do not save yet
                                    val db = FirebaseFirestore.getInstance()
                                    val userRef = db.collection("Users").document(userId)
                                    val snapshot = userRef.get().await()
                                    isLoading = false
                                    if (!snapshot.exists()) {
                                        errorMessage1 = null
                                        currentStep = RegistrationStep.STEP_2
                                    } else {
                                        errorMessage1 = "* ID taken. Try another ID."
                                    }
                                }
                            },
                            enabled = userId.length in 6..12 && password.length >= 8 && !isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xffff0088),
                                disabledContainerColor = Color(0xFF1C1C1E)
                            )
                        ) {
                            Text("Next", color = Color.White)
                        }
                    }

                    RegistrationStep.STEP_2 -> {
                        // Step 2: Name, Gender, Favorite Singer
                        Text(
                            text = "Tell us\nmore about you",
                            color = Color(0xFFECECEE),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(32.dp)) // 🆕 Add space below the title

                        // Name Field
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color(0xFFa6a7ab))) { append("Name") }
                                withStyle(style = SpanStyle(color = Color(0xFFFF0088))) { append(" *") }
                            },
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = {
                                Text(
                                    text = "ex. Changmo Kim",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 19.6.sp,
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF707073),
                                    )
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(13.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.DarkGray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Gender Selection
                        Text("Gender", color = Color(0xFFa6a7ab), fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { gender = "Male" },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (gender == "Male") Color(0xFFFF0088) else Color.Black,
                                ),
                                shape = RoundedCornerShape(6.dp),
                                border = if (gender == "Male") null else BorderStroke(
                                    1.dp,
                                    Color(0xFF29292c)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .width(146.dp)
                                    .height(48.dp)
                            ) { Text("Male") }

                            Button(
                                onClick = { gender = "Female" },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (gender == "Female") Color(0xFFFF0088) else Color.Black,
                                ),
                                shape = RoundedCornerShape(6.dp),
                                border = if (gender == "Female") null else BorderStroke(
                                    1.dp,
                                    Color(0xFF29292c)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .width(146.dp)
                                    .height(48.dp)
                            ) { Text("Female") }
                        }

                        Spacer(modifier = Modifier.height(24.dp)) // 🆕 Space before Favorite Singer

                        // Favorite Singer Field
                        Text(
                            text = "Favorite Singer",
                            color = Color(0xFFa6a7ab),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = favoriteSinger,
                            onValueChange = { favoriteSinger = it },
                            placeholder = {
                                Text(
                                    text = "ex) BTS, Bibi",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 19.6.sp,
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF434448),
                                    )
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(13.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.DarkGray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color.White
                            )
                        )


                        Spacer(modifier = Modifier.weight(1f))

                        if (isNameValid) {
                            Button(
                                onClick = {
                                    scope.launch {
                                        isLoading = true
                                        val success = registerUser(
                                            userId = userId,
                                            password = password,
                                            name = name,
                                            gender = gender,
                                            favoriteSinger = favoriteSinger
                                        )
                                        isLoading = false
                                        if (success) {
                                            onRegistrationSuccess()
                                        } else {
                                            errorMessage3 = "* Registration failed. Please try again."
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0088)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Done!", color = Color.White, fontSize = 18.sp)
                            }
                        }

                    }
            }
        }
    }
}
}


