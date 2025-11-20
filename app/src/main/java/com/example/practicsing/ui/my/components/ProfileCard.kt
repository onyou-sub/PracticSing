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
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF181818))
    ) {
        // 위쪽에 걸쳐 있는 아바타
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-32).dp)
        ) {
            // 바깥 핑크 링
            Box(
                modifier = Modifier
                    .size(82.dp)
                    .clip(CircleShape)
                    .background(PinkAccent.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                // 안쪽 원 (배경 검정)
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(DarkBackground),
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUrl.isNullOrBlank()) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MainText,
                            modifier = Modifier.size(36.dp)
                        )
                    } else {
                        AsyncImage(
                            model = profileImageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(76.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userName,
                color = MainText,
                style = Typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = email,
                color = Gray,
                style = Typography.bodySmall
            )

            Spacer(Modifier.height(18.dp))

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                border = BorderStroke(1.dp, MainText),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MainText
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Logout",
                    style = Typography.bodyMedium,
                    fontSize = 15.sp
                )
            }
        }
    }
}
