package com.example.practicsing.ui.song.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import com.example.practicsing.data.model.Song

@Composable
fun SongItem(song: Song, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {

        AsyncImage(
            model = song.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(song.level.name, color = Color(0xFF80A8FF), fontSize = 13.sp)

            Text(song.title, color = Color.White, fontSize = 18.sp)
            Text("${song.artist} · ${song.genre.name}", color = Color.Gray, fontSize = 14.sp)



            Text(
                "Participants ${song.participants}",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
