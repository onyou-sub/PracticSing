package com.example.practicsing.ui.diary

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
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
import com.example.practicsing.navigation.Screen
import com.example.practicsing.data.model.Diary
import androidx.compose.foundation.clickable
import com.example.practicsing.main.theme.BasePink
import com.example.practicsing.main.theme.Pink40
import com.example.practicsing.main.theme.Pink80
import com.example.practicsing.main.theme.PinkAccent
import android.net.Uri
@Composable
fun DiaryListScreen(
    navController: NavController
) {
    // userId 가져오기
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val userId = prefs.getString("userid", null) ?: ""

    // userId를 넣어서 ViewModel 생성
    val viewModel = remember { DiaryViewModel(userId) }

    val diaryList by viewModel.diaryList.collectAsState()

    // 실수로 삭제하는 행위 방지
    var showDeleteDialog by remember { mutableStateOf(false) }
    var targetDiaryId by remember { mutableStateOf("") }


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
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {


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


            if (diaryList.isEmpty()) {
                Text(
                    text = "No diary entries yet.",
                    color = Color.Gray,
                    style = Typography.bodyMedium
                )
            } else {
                diaryList.forEach { diary ->

                    DiaryListItem(
                        diary = diary,
                        onDelete = { id ->
                            targetDiaryId = id
                            showDeleteDialog = true
                        },
                        onEdit = { selectedDiary ->

                            val encodedTitle = Uri.encode(selectedDiary.title)
                            val encodedContent = Uri.encode(selectedDiary.content)

                            navController.navigate(
                                "diary_edit/${selectedDiary.id}?title=${encodedTitle}&content=${encodedContent}"
                            )
                        }
                    )

                    Spacer(Modifier.height(12.dp))
                }
            }

            // 삭제 확인 Dialog
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },

                    title = {
                        Text("Delete Diary", color = MainText)
                    },

                    text = {
                        Text(
                            "Are you sure you want to delete this diary?",
                            color = Color.LightGray
                        )
                    },

                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.deleteDiary(targetDiaryId)
                                showDeleteDialog = false
                            }
                        ) {
                            Text("Delete", color = BasePink)
                        }
                    },

                    dismissButton = {
                        TextButton(
                            onClick = { showDeleteDialog = false }
                        ) {
                            Text("Cancel", color = MainText)
                        }
                    },

                    containerColor = Color(0xFF1E1E1E),
                    tonalElevation = 8.dp
                )
            }


        }


        FloatingActionButton(
            onClick = { navController.navigate("diary_write") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Diary")
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}

@Composable
fun DiaryListItem(
    diary: Diary,
    onDelete: (String) -> Unit,
    onEdit: (Diary) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF262626), shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {

        //작성 날짜
        Text(
            text = formatDate(diary.date),
            color = Color.Gray,
            style = Typography.bodySmall
        )

        Spacer(Modifier.height(6.dp))


        // 제목
        Text(
            diary.title,
            color = MainText,
            style = Typography.titleMedium
        )

        Spacer(Modifier.height(4.dp))

        // 내용
        Text(
            diary.content,
            color = Color.LightGray,
            style = Typography.bodySmall
        )

        Spacer(Modifier.height(12.dp))

        // 삭제 버튼
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Delete",
                color = BasePink,
                style = Typography.bodyMedium,
                modifier = Modifier.clickable { onDelete(diary.id) }
            )
            Spacer(Modifier.width(10.dp))

            Text(
                text = "Update",
                color = BasePink,
                style = Typography.bodyMedium,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { onEdit(diary) }
            )
        }



    }


}
