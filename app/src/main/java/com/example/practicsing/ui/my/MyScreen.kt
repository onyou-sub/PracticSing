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

@Composable
fun MyScreen(
    navController: NavController
) {
    val context = LocalContext.current

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

        // 2. 프로필 카드
        ProfileCard(
            userName = "유정",
            email = "oujungzoey@gmail.com",
            profileImageUrl = null,
            onLogout = {
                // ✅ 로그아웃 처리
                val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                with(prefs.edit()) {
                    remove("userid")   // 필요하면 clear() 로 전체 삭제
                    apply()
                }

                // ✅ 네비게이션: 스택 비우고 로그인으로
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Main.route) { inclusive = true }
                }
            }
        )

        Spacer(Modifier.height(24.dp))

        // 3. Daily Practice 카드
        DailyPracticeCard(
            dateLabel = "11.10",
            streakCount = 1,
            totalSlots = 7
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
