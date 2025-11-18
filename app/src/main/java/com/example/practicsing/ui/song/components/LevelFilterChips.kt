package com.example.practicsing.ui.song.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicsing.data.model.Level

@Composable
fun LevelFilterChips(
    selected: Set<Level>,
    onToggle: (Level) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Level.values().forEach { level ->
            Box(
                modifier = Modifier
                    .background(
                        if (selected.contains(level)) Color(0xFFFF2FAB) else Color(0xFF2D2D2D),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable { onToggle(level) }
            ) {
                Text(
                    text = level.name,
                    color = if (selected.contains(level)) Color.White else Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
