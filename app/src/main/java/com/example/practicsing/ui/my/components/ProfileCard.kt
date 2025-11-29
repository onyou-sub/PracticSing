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

@Composable
fun ProfileCard(
    userName: String,
    email: String,
    profileImageUrl: String? = null,
    onLogout: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {

        // 1️⃣ 회색 카드(프레임)
        Column(
            modifier = Modifier
                .padding(top = 60.dp)          // ← 아바타가 카드 안으로 들어오도록 공간 확보
                .fillMaxWidth()
                .height(230.dp)               // ← 카드 높이 증가 (필수)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF1C1C1C)) // 좀 더 부드러운 어두운 회색
                .padding(
                    top = 70.dp,              // ← 아바타와 콘텐츠 간 간격
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 24.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = userName,
                color = MainText,
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = email,
                color = Gray,
                style = Typography.bodySmall
            )

            Spacer(Modifier.height(22.dp))

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                border = BorderStroke(1.dp, MainText),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MainText
                )
            ) {
                Text("Logout", fontSize = 15.sp)
            }
        }

        // 2️⃣ 핑크 링 + 프로필 아바타 (카드 위에 “완전히” 겹치기)
        Box(
            modifier = Modifier
                .offset(y = 10.dp)            // ← 아바타를 카드 안쪽으로 더 내려서 완전히 겹침
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


