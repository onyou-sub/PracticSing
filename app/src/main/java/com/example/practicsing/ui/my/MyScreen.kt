package com.example.practicsing.ui.my

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicsing.data.PracticePrefs
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.ui.common.AppScreenContainer
import com.example.practicsing.ui.my.components.DailyPracticeCard
import com.example.practicsing.ui.my.components.ProfileCard
import com.example.practicsing.ui.my.components.SongArchivePreviewCard
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun MyScreen(
    navController: NavController
) {
    val context = LocalContext.current

    // 로그인 유저 ID
    val prefs = remember { context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE) }
    val savedUserId = remember { prefs.getString("userid", null) }

    // Practice 상태 (오늘 했는지 / 연속 일수)
    var practicedToday by remember { mutableStateOf(false) }
    var streak by remember { mutableStateOf(1) }

    // 유저 이름 / 연습 히스토리
    var userName by remember { mutableStateOf("") }
    var history by remember { mutableStateOf<List<AiEvaluationResult>>(emptyList()) }

    val evaluationRepository = remember { EvaluationRepository() }

    // 🔹 Firestore에서 유저 이름 + 평가 기록 불러오기
    LaunchedEffect(savedUserId) {
        if (savedUserId != null) {
            try {
                val firestore = FirebaseFirestore.getInstance()

                val userSnapshot = firestore
                    .collection("Users")
                    .document(savedUserId)
                    .get()
                    .await()

                userName = userSnapshot.getString("Name") ?: ""

                history = evaluationRepository.getUserEvaluationHistory(savedUserId)
            } catch (e: Exception) {
                // TODO: 필요하면 Log.e 찍기
            }
        }
    }

    // 🔹 PracticePrefs에서 오늘 연습 여부 & 연속 일수 불러오기
    LaunchedEffect(Unit) {
        practicedToday = PracticePrefs.hasPracticedToday(context)
        streak = PracticePrefs.getCurrentDay(context)
    }

    AppScreenContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // 상단 타이틀
            Text(
                text = "My Page",
                color = MainText,
                style = Typography.headlineSmall
            )

            Spacer(Modifier.height(4.dp))

            if (userName.isNotBlank()) {
                Text(
                    text = "$userName, Shall we practice again today?",
                    color = Gray,
                    style = Typography.bodyMedium
                )
            }

            Spacer(Modifier.height(24.dp))

            // 프로필 카드
            ProfileCard(
                userName = userName,
                profileImageUrl = null,
                onLogout = {
                    with(prefs.edit()) {
                        remove("userid")
                        apply()
                    }
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )

            Spacer(Modifier.height(24.dp))

            // 🔹 오늘 연습 여부 + 연속 일수 보여주는 카드
            DailyPracticeCard(
                dateLabel = "Today",
                streakCount = streak,
                totalSlots = 7,
                practicedToday = practicedToday
            )

            Spacer(Modifier.height(32.dp))

            // Song Archive 헤더
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Song Archive",
                        color = MainText,
                        style = Typography.bodyLarge
                    )
                    Text(
                        text = "The songs I've tried",
                        color = Gray,
                        style = Typography.bodySmall
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.SongArchive.route)
                    }
                ) {
                    Text(
                        text = "See all",
                        color = Gray,
                        style = Typography.bodyMedium
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Go to Song Archive",
                        tint = Gray
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // 실제 이 유저가 부른 노래들만 미리보기로 표시
            if (history.isEmpty()) {
                Text(
                    text = "No Songs Tried Yet.",
                    color = Gray,
                    style = Typography.bodySmall
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    history.take(5).forEach { item ->
                        SongArchivePreviewCard(
                            title = item.songTitle,
                            date = item.practicedDateText.ifBlank { item.durationText },
                            imageUrl = item.albumImageUrl,
                            onClick = {
                                navController.navigate("evaluationDetail/${item.id}")
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Diary 섹션
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "My Diary",
                        color = MainText,
                        style = Typography.bodyLarge
                    )
                    Text(
                        text = "Write your daily practice",
                        color = Gray,
                        style = Typography.bodySmall
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navController.navigate("diary_list")
                    }
                ) {
                    Text(
                        text = "Go",
                        color = Gray,
                        style = Typography.bodyMedium
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Go to Diary",
                        tint = Gray
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
