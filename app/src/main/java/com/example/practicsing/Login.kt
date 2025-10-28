package com.example.practicsing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()
    val snackbar = remember { SnackbarHostState() }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {

            if (user.isBlank() || pass.isBlank()) {
                msg = "Please enter email and password"
                return@Button
            }

            db.collection("Users").document(user).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("Name")
                        val password = document.getString("Password")

                        if (name == user && pass == password) {
                            onLoginSuccess()
                        } else {
                            msg = "Incorrect password/email"
                        }
                    } else {
                        msg = "User not found"
                    }
                }
                .addOnFailureListener {
                    msg = "Error accessing database"
                }

        }) {
            Text("Login")
        }

        LaunchedEffect(msg) {
            if (msg.isNotEmpty()) {
                snackbar.showSnackbar(msg)
            }
        }

        SnackbarHost(hostState = snackbar)
    }
}

