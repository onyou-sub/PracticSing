package com.example.practicsing.ui.song.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.practicsing.data.model.Song
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography

@Composable
fun SongDetailContent(
    song: Song
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBackground)
    ) {

        // 🔹 상단: 앨범이미지 + 딤 배경 + 타이틀 영역
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            // 뒤에 깔리는 큰 앨범 이미지
            AsyncImage(
                model = song.imageUrl,
                contentDescription = song.title,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.9f),
                contentScale = ContentScale.Crop
            )

            // 위에서 아래로 어두워지는 그라데이션 (딤)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.6f),
                                Color.Black.copy(alpha = 0.3f),
                                DarkBackground
                            )
                        )
                    )
            )

            // 가운데 카드형 앨범 커버 + 텍스트
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 앞에 나오는 정사각형 앨범 커버
                AsyncImage(
                    model = song.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // 난이도 배지 (Level)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0xFF2D2D2D))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = song.level.name,     // e.g. "Hard"
                            color = PinkAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = song.title,
                        color = MainText,
                        style = Typography.titleLarge
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = song.artist,
                        color = Gray,
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.height(2.dp))

                    Text(
                        text = song.releaseDate,   // "2023.06.04"
                        color = Gray,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 곡 설명
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = song.description,
                color = MainText,
                style = Typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))

            // 아래 구분선 느낌
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFF2A2A2A))
            )

            Spacer(Modifier.height(12.dp))
        }
    }
}
