package com.example.practicsing.ui.song.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.practicsing.data.model.Level
import com.example.practicsing.main.theme.*

@Composable
fun LevelFilterChips(
    selected: Set<Level>,
    onToggle: (Level) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Level.values().forEach { level ->
            val isSelected = selected.contains(level)

            Surface(
                onClick = { onToggle(level) },
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) PinkAccent else DarkBackground.copy(alpha = 0.9f),
                tonalElevation = 0.dp,
                modifier = Modifier
                    // 좌우 간격 줄이기: 6dp 정도
                    .padding(end = 6.dp)
            ) {
                Text(
                    text = level.name.replaceFirstChar { it.uppercase() },
                    color = if (isSelected) MainText else Gray,
                    style = Typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }
    }
}
