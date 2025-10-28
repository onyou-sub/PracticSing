package com.example.practicsing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(onRegistrationSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Select Gender") }
        var expanded by remember { mutableStateOf(false) }
        val genders = listOf("Male", "Female", "Other")
    var favorite by remember { mutableStateOf("") }
    var check by remember { mutableStateOf(false) }

    var msg by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()
    val snackbar = remember { SnackbarHostState() }

    var loading by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = pass,
                    onValueChange = { pass = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Box {
                    OutlinedTextField(
                        value = selectedGender,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        label = { Text("Gender") },
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown"
                                )
                            }
                        }
                    )


                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { expanded = true }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        genders.forEach { gender ->
                            DropdownMenuItem(
                                text = { Text(gender) },
                                onClick = {
                                    selectedGender = gender
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = favorite,
                    onValueChange = { favorite = it },
                    label = { Text("Favorite Singer") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = check,
                        onCheckedChange = { check = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (check) "Agreed" else "Agree to Terms of Service")
                }

                Button(
                    onClick = {
                        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()

                        if (email.isBlank() || pass.isBlank()) { msg = "Please enter email and password" ; return@Button}
                        if (!emailRegex.matches(email)) { msg = "Please enter a valid email address" ; return@Button}
                        if (email.length > 50){ msg = "Email is too long" ; return@Button}
                        if (pass.length > 30) { msg = "Password is too long" ; return@Button}
                        if (name.isBlank()) { msg = "Please enter your name" ; return@Button}
                        if (name.length > 30) { msg = "Name is too long" ; return@Button}
                        if (selectedGender == "Select Gender") { msg = "Please select your gender" ; return@Button}
                        if (!check) { msg = "You must agree to the terms and conditions" ; return@Button}
                        if (favorite.length > 30){ msg = "Favorite singer name is too long"; return@Button}

                        loading = true

                        val emailRef = db.collection("Users").document(email)
                        emailRef.get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    msg = "Email already in use"
                                    loading = false
                                } else {
                                    emailRef.set(
                                        mapOf(
                                            "Name" to name,
                                            "Password" to pass,
                                            "Gender" to selectedGender,
                                            "Favorite" to favorite
                                        )
                                    )
                                        .addOnSuccessListener {
                                            msg = "Registration successful!"
                                            loading = false
                                            onRegistrationSuccess()
                                        }
                                        .addOnFailureListener {
                                            msg = "Error saving data"
                                            loading = false
                                        }
                                }
                            }
                            .addOnFailureListener {
                                msg = "Error accessing database"
                                loading = false
                            }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !loading
                ) {
                    Text(if (loading) "Registering..." else "Register")
                }
            }


            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }


            LaunchedEffect(msg) {
                if (msg.isNotEmpty()) {
                    snackbar.showSnackbar(msg)
                }
            }
        }
    }
}
