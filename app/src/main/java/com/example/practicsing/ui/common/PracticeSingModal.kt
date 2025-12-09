package com.example.practicsing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography

@Composable
fun PracticeSingModal(
    visible: Boolean,
    emoji: String,
    title: String,
    subtitle: String? = null,
    buttonText: String,
    onDismissRequest: () -> Unit,
    onButtonClick: () -> Unit = onDismissRequest,
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true // 밖 클릭 시에 모달 닫히도록
        )
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = DarkBackground, // 카드 배경
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 28.dp)
                    .widthIn(min = 260.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 이모지 동그란 배경
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = androidx.compose.ui.graphics.Color(0xFF2C2C2C),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = Typography.headlineMedium.fontSize)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 텍스트
                Text(
                    text = title,
                    style = Typography.bodyLarge,
                    color = MainText,
                    textAlign = TextAlign.Center
                )

                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = subtitle,
                        style = Typography.bodyMedium,
                        color = MainText.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 버튼
                Button(
                    onClick = onButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinkAccent,
                        contentColor = androidx.compose.ui.graphics.Color.White
                    )
                ) {
                    Text(
                        text = buttonText,
                        style = Typography.labelLarge
                    )
                }
            }
        }
    }
}
