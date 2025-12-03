package com.example.practicsing.ui.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.ui.common.RoundedBackButton
import com.example.practicsing.ui.my.diary.DiaryViewModel

@Composable
fun DiaryScreen(
    navController: NavController,
    viewModel: DiaryViewModel = DiaryViewModel()
) {
    val diaryList by viewModel.diaryList.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    var editMode by remember { mutableStateOf(false) }
    var editDiaryId by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {
        // 🔙 상단 왼쪽 뒤로가기 버튼
        RoundedBackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            onClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp) // 뒤로가기 버튼 공간 확보
        ) {

            // 중앙 타이틀
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "My Diary",
                    color = MainText,
                    style = Typography.headlineSmall
                )
            }

            Spacer(Modifier.height(20.dp))

            // 입력 UI
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    if (!editMode) {
                        viewModel.addDiary(title, content)
                    } else {
                        viewModel.updateDiary(
                            com.example.practicsing.data.model.Diary(
                                id = editDiaryId,
                                title = title,
                                content = content
                            )
                        )
                        editMode = false
                        editDiaryId = ""
                    }

                    title = ""
                    content = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (editMode) "Update Diary" else "Add Diary")
            }

            Spacer(Modifier.height(20.dp))

            Divider(color = Color.Gray.copy(alpha = 0.3f))

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Your Diaries",
                style = Typography.titleMedium,
                color = MainText
            )

            Spacer(Modifier.height(12.dp))

            diaryList.forEach { diary ->
                DiaryItem(
                    title = diary.title,
                    content = diary.content,
                    onEdit = {
                        editMode = true
                        editDiaryId = diary.id
                        title = diary.title
                        content = diary.content
                    },
                    onDelete = { viewModel.deleteDiary(diary.id) }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun DiaryItem(
    title: String,
    content: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(text = title, style = Typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = content, style = Typography.bodyMedium, color = Color.DarkGray)

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.size(24.dp).clickable { onEdit() }
                )
                Spacer(Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp).clickable { onDelete() }
                )
            }
        }
    }
}
