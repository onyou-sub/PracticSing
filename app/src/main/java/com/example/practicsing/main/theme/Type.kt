package com.example.practicsing.main.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.practicsing.R

// Pretendard Variable 폰트 패밀리 정의
// Variable 폰트는 하나의 파일로 여러 굵기를 지원합니다.
val Pretendard = FontFamily(
    // R.font.pretendard_variable은 res/font 폴더에 파일이 있다고 가정합니다.
    Font(
        resId = R.font.PretendardVariable,
        weight = FontWeight.Normal // 모든 굵기는 이 하나의 폰트 파일을 통해 처리됩니다.
    )
)

// 사용자 정의 Typography 스타일
val Typography = Typography(
    // 1. 메인 타이틀 (Main Header/Title)
    // 요청: main은 24, bold
    headlineSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    // 2. 일반 글자 (Body Text)
    // 요청: 일반 글자는 medium 14
    bodyMedium = TextStyle( // bodyLarge 대신 bodyMedium 사용
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp // 가독성을 위해 라인 높이 추가 (선택 사항)
    ),

    // 3. CTA (Button/Call-to-Action Text)
    // 요청: CTA에 들어가는 건 Semibold 16
    titleMedium = TextStyle( // 버튼 텍스트에 적합한 titleMedium 사용
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    // 4. 작은 알림 글씨 (Small Alert/Caption Text)
    // 요청: 작은 alert 글씨는 light의 12
    labelSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),

    // 5. 인사말 타이틀 (Greeting Title)
    // 요청: 인삿말은 title은 20
    headlineMedium = TextStyle( // 큰 제목에 적합한 headlineMedium 사용
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold, // Bold 사용 (타이틀이므로)
        fontSize = 20.sp
    ),

    // 6. 인사말 서브타이틀 (Greeting Subtitle)
    // 요청: SUbtitle은 16
    bodyLarge = TextStyle( // 일반 텍스트 중 가장 큰 bodyLarge 사용
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium, // Medium 사용 (서브 타이틀이므로)
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
)