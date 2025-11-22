package com.example.practicsing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
// Theme 및 Data Model Import
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.data.model.Song // Song 데이터 모델 사용

@Composable
fun SongCard(song: Song) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(end = 12.dp)
            .clickable { /* TODO: Song Detail로 이동 */ }
    ) {

        AsyncImage(
            model = song.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(92.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 제목
        Text(text = song.title, color = MainText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        // 아티스트, 장르
        Text(text = "${song.artist} · ${song.genre}", color = Gray, fontSize = 12.sp)
        // 참여자 수
        Text(text = "Participants ${song.participants}", color = MainText, fontSize = 12.sp)
    }
}