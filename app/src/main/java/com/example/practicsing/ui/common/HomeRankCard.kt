package com.example.practicsing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import androidx.compose.ui.text.font.FontWeight

data class HomeRankUi(
    val profileImage: String,
    val userName: String,
    val songTitle: String,
    val artist: String,
    val score: Int
)

@Composable
fun HomeRankCard(rank: HomeRankUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            AsyncImage(
                model = rank.profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Gray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = rank.userName,
                    color = MainText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = "${rank.songTitle} (${rank.artist})",
                    color = Gray,
                    fontSize = 12.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .border(2.dp, PinkAccent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rank.score.toString(),
                color = PinkAccent,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
