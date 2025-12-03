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

    // SharedPreferences에서 userId 가져오기
    val prefs = remember { context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE) }
    val savedUserId = remember { prefs.getString("userid", null) }

    var practicedToday by remember { mutableStateOf(false) }
    var streak by remember { mutableStateOf(1) }

    var userName by remember { mutableStateOf("") }

    // 이 유저가 실제로 부른 기록들 (AiEvaluationResult)
    var history by remember { mutableStateOf<List<AiEvaluationResult>>(emptyList()) }

    val evaluationRepository = remember { EvaluationRepository() }

    // 유저 정보 + 평가 기록 불러오기
    LaunchedEffect(savedUserId) {
        if (savedUserId != null) {
            val firestore = FirebaseFirestore.getInstance()

            // Users 컬렉션에서 유저 이름 가져오기
            val userSnapshot = firestore
                .collection("Users")
                .document(savedUserId)
                .get()
                .await()

            userName = userSnapshot.getString("Name") ?: ""

            // 이 유저가 실제로 부른 노래들(평가 기록) 가져오기
            history = evaluationRepository.getUserEvaluationHistory(savedUserId)
        }
    }

    // 연습 여부 / 연속 일수
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
                // 필요하면 Log 찍기
            }
        }
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
                    text = "$userName 님, 오늘도 연습해볼까요?",
                    color = Gray,
                    style = Typography.bodyMedium
                )
            }

            Spacer(Modifier.height(24.dp))

            // 프로필 카드 (favorite singer 제거)
            ProfileCard(
                userName = userName, // 더 이상 favorite singer 사용 X
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

            // Daily Practice - streak + 동그라미 체크들
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
                    // 너무 많을 수 있으니 상위 3~5개만 노출
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
                        navController.navigate("diary")
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
