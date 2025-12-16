package com.example.practicsing.ui.song.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import com.example.practicsing.data.model.Genre
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.Typography

@Composable
fun SongCategoryTabs(
    selected: String,
    onSelect: (String) -> Unit
) {
    val fixedTabs = listOf("HOT", "RECENT")

    // 🔹 Genre Enum 기반으로 스크롤 영역 구성 (POP은 제외)
    val scrollTabs: List<String> =
        Genre.values()
            .filter { it != Genre.POP }      // Figma 상 POP은 탭에 없어서 제외
            .map { it.name }                 // "Ballad", "Acoustic", "Dance", "OST", "Etc"

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // HOT / RECENT (고정)
        fixedTabs.forEach { tab ->
            val isSelected = selected == tab
            Text(
                text = tab,
                color = if (isSelected) PinkAccent else MainText,
                style = Typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onSelect(tab) }
            )
        }

        // 구분용 |
        Text(
            text = "|",
            color = Gray,
            style = Typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 6.dp)
        )

        // 나머지 카테고리 가로 스크롤
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            scrollTabs.forEach { genreName ->
                val isSelected = selected == genreName
                Text(
                    text = genreName,  // "Ballad", "Acoustic" 등
                    color = if (isSelected) PinkAccent else MainText,
                    style = Typography.bodyMedium,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable { onSelect(genreName) }
                )
            }
        }
    }
}
