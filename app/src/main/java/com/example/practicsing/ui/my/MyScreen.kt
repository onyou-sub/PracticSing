package com.example.practicsing.ui.my

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.ui.common.AppScreenContainer
import com.example.practicsing.ui.my.components.DailyPracticeCard
import com.example.practicsing.ui.my.components.ProfileCard
import com.example.practicsing.ui.my.components.SongArchivePreviewCard
import com.example.practicsing.data.PracticePrefs
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
@Composable
fun MyScreen(
    navController: NavController
) {
    val context = LocalContext.current

    var practicedToday by remember { mutableStateOf(false) }
    var streak by remember { mutableStateOf(1) }
    
    //정보 불러오기
    val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val savedUserId = prefs.getString("userid", null)

    var userName by remember { mutableStateOf("") }
    var favoriteSinger by remember { mutableStateOf("") }


    LaunchedEffect(savedUserId) {
        if (savedUserId != null) {
            val snapshot = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(savedUserId)
                .get()
                .await()

            userName = snapshot.getString("Name") ?: "Unknown"
            favoriteSinger = snapshot.getString("FavoriteSinger") ?: "Unknown"
        }
    }

    LaunchedEffect(Unit) {
        practicedToday = PracticePrefs.didPracticeToday(context)
        streak = PracticePrefs.getCurrentDay(context)
    }

    AppScreenContainer {

        // 상태바와 겹치지 않게 살짝 아래로
        Spacer(Modifier.height(8.dp))

        // 1. 타이틀
        Text(
            text = "Mypage",
            color = MainText,
            style = Typography.headlineSmall
        )

        Spacer(Modifier.height(24.dp))

        //실제 로그인 정보 불러오기
        ProfileCard(
            userName = userName,
            email = favoriteSinger,
            profileImageUrl = null,
            onLogout = {
                val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
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

        // 3. Daily Practice 카드
        DailyPracticeCard(
            dateLabel = "Today",
            streakCount = streak,
            totalSlots = 7,
            practicedToday = practicedToday
        )

        Spacer(Modifier.height(24.dp))

        // 4. Song Archive 헤더
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Song Archive",
                color = MainText,
                style = Typography.bodyLarge
            )
            Text(
                text = ">",
                color = Gray,
                style = Typography.bodyLarge,
                modifier = Modifier.clickable {
                    // TODO: Archive 리스트 화면으로 이동
                    navController.navigate(Screen.SongArchive.route)
                }
            )
        }

        Spacer(Modifier.height(12.dp))

        // 5. Song Archive 프리뷰
        SongArchivePreviewCard(
            title = "Without You",
            date = "2025.11.12",
            imageUrl = "https://picsum.photos/600/300",
            onClick = {
                // TODO: 해당 곡/녹음 상세로 이동
            }
        )
    }
}
