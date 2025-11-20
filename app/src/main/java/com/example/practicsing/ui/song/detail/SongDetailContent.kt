package com.example.practicsing.ui.song.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.practicsing.data.model.Song
import com.example.practicsing.main.theme.*

@Composable
fun SongDetailContent(
    song: Song
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // 앨범 이미지 + 레벨 태그
        Box {
            AsyncImage(
                model = song.imageUrl,             // 🔹 Song.imageUrl 사용
                contentDescription = null,
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            // Level 태그
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
                    .background(PinkAccent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = song.level.name,        // e.g. "Easy", "Hard"
                    color = MainText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {

            // 제목 + HOT 뱃지
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = song.title,
                    color = MainText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                // 🔹 participants 기준으로 HOT 여부 간단 계산 (원하면 조건 바꿔도 됨)
                val isHot = song.participants >= 200
                if (isHot) {
                    Spacer(modifier = Modifier.width(6.dp))
                    HotBadge()
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${song.artist} · ${song.genre.name}",
                color = Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = song.description,
                color = Color(0xFFDDDDDD),
                fontSize = 13.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Participants ${song.participants}",
                color = Gray,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun HotBadge() {
    Box(
        modifier = Modifier
            .background(Color(0xFFFF4040), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            "HOT",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
