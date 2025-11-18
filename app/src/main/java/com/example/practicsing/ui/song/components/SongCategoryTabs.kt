package com.example.practicsing.ui.song.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SongCategoryTabs(
    selected: String,
    onSelect: (String) -> Unit
) {
    val categories = listOf(
        "HOT", "RECENT", "Ballad", "Acoustic", "Dance", "OST", "Etc"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        categories.forEach { c ->
            Text(
                text = c,
                color = if (c == selected) Color(0xFFFF2FAB) else Color.Gray,
                modifier = Modifier.clickable { onSelect(c) },
                fontSize = 16.sp
            )
        }
    }
}
