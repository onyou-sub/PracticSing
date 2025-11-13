package com.example.practicsing.ui.song.practice

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SongPracticeContent(
    songId: Long,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        // --- Header 영역 (Back + Title 자리) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // 나중에 공통 Header 컴포넌트로 교체할 예정
            Text(
                text = "Back (placeholder)",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Practice Screen Title or Info ---
        Text(
            text = "Practice Mode",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        // --- Song ID (임시 표시, 실제로는 서버에서 데이터 읽어올 예정) ---
        Text(
            text = "Song ID: $songId",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- Main Practice 영역 (가사/미디/녹음/피치 등 추후 추가) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // 화면 중앙 영역 대부분 차지
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SongPracticeContent UI will be implemented later.",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Bottom Controller 영역 (재생/정지/다음줄 등) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bottom controller (placeholder)",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
