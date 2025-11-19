@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.practicsing.ui.my

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography

@Composable
fun ArchivePlayerDialog(
    record: PracticeRecord,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
    onShowEvaluation: () -> Unit
) {
    // 간단한 진행도 상태 (실제 오디오랑은 나중에 연결)
    var progress by remember { mutableStateOf(0.3f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000))      // 반투명 블랙
            .clickable(onClick = onDismiss, indication = null, interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() })
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF141414)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 앨범 이미지
                AsyncImage(
                    model = record.albumImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = record.songTitle,
                    color = MainText,
                    style = Typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = record.recordedDate,
                    color = MainText,
                    style = Typography.bodySmall
                )

                Spacer(Modifier.height(24.dp))

                // 슬라이더 + 시간
                Slider(
                    value = progress,
                    onValueChange = { progress = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = PinkAccent,
                        activeTrackColor = PinkAccent,
                        inactiveTrackColor = Color(0xFF444444)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "1:25", color = MainText, style = Typography.labelSmall)
                    Text(
                        text = record.durationText, // 끝 시점 텍스트
                        color = MainText,
                        style = Typography.labelSmall
                    )
                }

                Spacer(Modifier.height(24.dp))

                // 하단 아이콘들 : Retry / Play / 메뉴
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Replay,
                        contentDescription = "Retry",
                        tint = MainText,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onRetry() }
                    )

                    Spacer(Modifier.width(40.dp))

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(PinkAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Pause,
                            contentDescription = "Play/Pause",
                            tint = MainText,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(Modifier.width(40.dp))

                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "AI Evaluation",
                        tint = MainText,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onShowEvaluation() }
                    )
                }
            }
        }
    }
}
