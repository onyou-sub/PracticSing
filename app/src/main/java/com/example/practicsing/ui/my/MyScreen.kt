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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.repository.PracticeRepositoryImpl
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
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
    val lifecycleOwner = LocalLifecycleOwner.current

    val prefs = remember {
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }
    val savedUserId = remember { prefs.getString("userid", null) }

    var practicedToday by remember { mutableStateOf(false) }
    var streak by remember { mutableStateOf(1) }

    var userName by remember { mutableStateOf("") }
    var history by remember { mutableStateOf<List<AiEvaluationResult>>(emptyList()) }

    // 화면이 다시 보일 때마다 리로드
    var refreshTrigger by remember { mutableStateOf(0) }

    val evaluationRepository = remember { EvaluationRepository() }
    val practiceRepository = remember { PracticeRepositoryImpl() }

    // Logout 모달
    var showLogoutDialog by remember { mutableStateOf(false) }

    // lifecycle 감지해서 ON_RESUME 때 refreshTrigger 증가
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                refreshTrigger++
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(savedUserId, refreshTrigger) {
        if (savedUserId != null) {
            try {
                val firestore = FirebaseFirestore.getInstance()

                // 유저 이름
                val userSnapshot = firestore
                    .collection("Users")
                    .document(savedUserId)
                    .get()
                    .await()
                userName = userSnapshot.getString("Name") ?: ""

                // AI 평가 히스토리
                history = evaluationRepository.getUserEvaluationHistory(savedUserId)

                // 연속 출석 / 오늘 연습 여부
                streak = practiceRepository.getCurrentStreak(savedUserId)
                practicedToday = practiceRepository.hasPracticedToday(savedUserId)

            } catch (_: Exception) {
                // TODO: 에러 토스트 등 필요하면 추가
            }
        }
    }

    AppScreenContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            // ---------- Header ----------
            Text(
                text = "My Page",
                color = MainText,
                style = Typography.headlineSmall
            )

            Spacer(Modifier.height(20.dp))

            // ---------- Profile ----------
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

            // ---------- Song Archive 섹션 ----------
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

                    // 상단 타이틀 + See all
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
                        // 🔹 비어 있을 때 안내 문구
                        Text(
                            text = "아직 평가가 저장된 노래가 없어요.\n노래를 녹음하고 SAVE를 눌러보세요!",
                            color = Gray,
                            style = Typography.bodySmall
                        )
                    } else {
                        // 🔹 최근 3개 프리뷰 카드
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            history
                                .sortedByDescending { it.practicedAtMillis }
                                .take(3)
                                .forEach { item ->
                                    SongArchivePreviewCard(
                                        title = item.songTitle,
                                        date = item.practicedDateText.ifBlank { item.durationText },
                                        imageUrl = item.albumImageUrl,
                                        onClick = {
                                            // 나중에 상세/플레이 화면이랑 연결
                                            navController.navigate("evaluationDetail/${item.id}")
                                        }
                                    )
                                }
                        }
                    }
                }
            }

            // 아래로 쭉 밀기
            Spacer(Modifier.weight(1f))

            // ---------- Logout 영역 (카드처럼 정렬) ----------
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = DarkBackground.copy(alpha = 0.7f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showLogoutDialog = true }
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Logout",
                        color = Gray,
                        style = Typography.bodyMedium
                    )
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = Gray
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

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
