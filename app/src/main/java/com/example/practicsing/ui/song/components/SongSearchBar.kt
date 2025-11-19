package com.example.practicsing.ui.song.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SongSearchBar(
    modifier: Modifier = Modifier,      // modifier 파라미터 추가
    onClick: () -> Unit
) {
    Box(
        modifier = modifier             // 밖에서 넘겨준 modifier 먼저 적용
            .fillMaxWidth()
            .height(52.dp)
            .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Search for favorite song",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
