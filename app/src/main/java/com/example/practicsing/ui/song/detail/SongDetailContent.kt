package com.example.practicsing.ui.song.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.practicsing.R

@Composable
fun SongDetailContent(
    songId: Long,
    navController: NavHostController
) {
    // 나중에 ViewModel에서 songId로 데이터 가져오면 됨
    val song = SampleSongDetail()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // 앨범 이미지 + 레벨 태그
        Box {
            Image(
                painter = painterResource(id = song.albumImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            // Level 태그
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
                    .background(Color(0xFF3A8DFF), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = song.level,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {

            // 제목 + HOT
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = song.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if (song.isHot) {
                    Spacer(modifier = Modifier.width(6.dp))
                    HotBadge()
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${song.singer} · ${song.genre}",
                color = Color(0xFFBBBBBB),
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
                color = Color(0xFFBBBBBB),
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

data class SongDetailUiModel(
    val title: String,
    val singer: String,
    val genre: String,
    val isHot: Boolean,
    val participants: Int,
    val level: String,
    val albumImage: Int,
    val description: String
)

fun SampleSongDetail() = SongDetailUiModel(
    title = "Spring Day",
    singer = "BTS",
    genre = "Ballad",
    isHot = true,
    participants = 306,
    level = "Easy",
    albumImage = R.drawable.sample_album,
    description = "A heartfelt song that helps you practice emotional tone."
)
