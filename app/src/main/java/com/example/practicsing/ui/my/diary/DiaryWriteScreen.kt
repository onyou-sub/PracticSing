package com.example.practicsing.ui.diary

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.ui.common.RoundedBackButton
import com.example.practicsing.ui.my.diary.DiaryViewModel
import com.example.practicsing.data.model.Diary
import androidx.compose.material3.TextFieldDefaults
import com.example.practicsing.main.theme.BasePink

@Composable
fun DiaryWriteScreen(
    navController: NavController
) {
    // userId 가져오기
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val userId = prefs.getString("userid", null) ?: ""

    // userId 로 ViewModel 준비
    val viewModel = remember { DiaryViewModel(userId) }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {


        RoundedBackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            onClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        ) {


            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Write Diary",
                    style = Typography.headlineSmall,
                    color = MainText
                )
            }

            Spacer(Modifier.height(20.dp))


            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(Modifier.height(12.dp))


            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(Modifier.height(20.dp))


            Button(
                onClick = {
                    if (title.isNotBlank() || content.isNotBlank()) {
                        viewModel.addDiary(title, content)
                        navController.popBackStack()  // 저장 후 뒤로가기
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                        containerColor = BasePink,
                contentColor = Color.White
            )
            ) {
                Text("Save Diary")
            }
        }
    }
}
