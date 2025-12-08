// file: app/src/main/java/com/example/practicsing/ui/my/components/ProfileCard.kt
package com.example.practicsing.ui.my.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import coil.compose.AsyncImage
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke

// file: ProfileCard.kt
@Composable
fun ProfileCard(
    userName: String,
    profileImageUrl: String? = null,
    onDiaryClick: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {

        // 회색 카드 영역
        Column(
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth()
                .wrapContentHeight() // ❗ 높이를 자동 맞추게 변경
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF1C1C1C))
                .padding(
                    top = 70.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp // 🔽 기존보다 작게(24 → 20)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 유저 이름
            Text(
                text = userName,
                color = MainText,
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(20.dp))

            // 🔥 핑크색 꽉 찬 버튼: Singing Diary
            Button(
                onClick = onDiaryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkAccent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "My Diary",
                    style = Typography.labelLarge
                )
            }
        }

        // 아바타
        Box(
            modifier = Modifier.offset(y = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                PinkAccent.copy(alpha = 0.95f),
                                PinkAccent.copy(alpha = 0.65f)
                            )
                        )
                    )
                    .shadow(12.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(DarkBackground),
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUrl.isNullOrBlank()) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = MainText,
                            modifier = Modifier.size(44.dp)
                        )
                    } else {
                        AsyncImage(
                            model = profileImageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(84.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }
        }
    }
}
