package com.example.practicsing.ui.my

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.ui.common.AppScreenContainer
import com.example.practicsing.ui.common.PracticeSingModal
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

    val prefs = remember {
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }
    val savedUserId = remember { prefs.getString("userid", null) }

    var practicedToday by remember { mutableStateOf(false) }
    var streak by remember { mutableStateOf(1) }

    var userName by remember { mutableStateOf("") }
    var history by remember { mutableStateOf<List<AiEvaluationResult>>(emptyList()) }

    val evaluationRepository = remember { EvaluationRepository() }

    // Logout 모달 표시 여부
    var showLogoutDialog by remember { mutableStateOf(false) }

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
            } catch (_: Exception) {}
        }
    }

    LaunchedEffect(Unit) {
        practicedToday = PracticePrefs.hasPracticedToday(context)
        streak = PracticePrefs.getCurrentDay(context)
    }

    AppScreenContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            // ---------- My Page Header ----------
            Text(
                text = "My Page",
                color = MainText,
                style = Typography.headlineSmall
            )

            Spacer(Modifier.height(20.dp))

            // ---------- Profile Card (버튼 = My Diary) ----------
            ProfileCard(
                userName = userName,
                profileImageUrl = null,
                onDiaryClick = {
                    navController.navigate("diary_list")
                }
            )

            Spacer(Modifier.height(24.dp))

            // ---------- Daily Practice ----------
            DailyPracticeCard(
                dateLabel = "Today",
                streakCount = streak,
                totalSlots = 7,
                practicedToday = practicedToday
            )

            Spacer(Modifier.height(28.dp))

            // ---------- Song Archive (div 박스) ----------
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = DarkBackground.copy(alpha = 0.7f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {

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
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.SongArchive.route)
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("See all", color = Gray, style = Typography.bodyMedium)
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = null,
                                tint = Gray
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    if (history.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Songs Tried Yet.",
                                color = Gray,
                                style = Typography.bodySmall
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            history.take(3).forEach { item ->
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
                }
            }

            // 아래 공간 밀기
            Spacer(Modifier.weight(1f))

            // ---------- Logout (왼쪽 정렬 + 이모지 + 모달 호출) ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { showLogoutDialog = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Logout",
                    color = Gray,
                    style = Typography.bodyMedium
                )
            }

            // ---------- Logout Modal ----------
            PracticeSingModal(
                visible = showLogoutDialog,
                emoji = "😅",
                title = "Are you sure to logout?",
                subtitle = "Hope to see you again.",
                buttonText = "Logout",
                onDismissRequest = { showLogoutDialog = false },
                onButtonClick = {
                    showLogoutDialog = false
                    with(prefs.edit()) {
                        remove("userid")
                        apply()
                    }
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
