package com.example.practicsing.ui.pract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.navigation.TopBar
import kotlinx.coroutines.delay

// -----------------------------
//  Practice Step Enum
// -----------------------------
enum class PracticeStep {
    Splash,
    BreathIntro,
    BreathCount,
    TonePitch,
    Pronunciation,
    Done
}

// -----------------------------
//  Practice Screen Navigation
// -----------------------------
@Composable
fun PracticeScreen(
    navController: NavController

) {
    var step by remember { mutableStateOf(PracticeStep.Splash) }

    when (step) {

        PracticeStep.Splash -> StartScreen(
            onNext = { step = PracticeStep.BreathIntro }
        )

        PracticeStep.BreathIntro -> BreathIntroScreen(
            onStart = { step = PracticeStep.BreathCount }
        )

        PracticeStep.BreathCount -> BreathCountScreen(
            onFinish = {step = PracticeStep.TonePitch

            }
        )
        PracticeStep.TonePitch -> TonePitchScreen(
            onNext = { step = PracticeStep.Pronunciation}
        )
        PracticeStep.Pronunciation  -> PronunciationScreen(
            onFinish = { step = PracticeStep.Done }
        )
        PracticeStep.Done -> DoneScreen(
            onFinish = {
                navController.navigate("home") {
                    popUpTo("practice") { inclusive = true }
                }
            }
        )
    }
}


@Composable
fun StartScreen(onNext: () -> Unit) {

    LaunchedEffect(true) {
        delay(2000)
        onNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Daily Practice", color = Color.White, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(containerColor = PinkAccent)
            ) {
                Text("Day 1")
            }
        }
    }
}

@Composable
fun DoneScreen(onFinish: () -> Unit){
    LaunchedEffect(true) {
        delay(2000)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Done for today!", color = Color.White, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(20.dp))


        }
    }
}
@Composable
fun BreathIntroScreen(onStart: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        TopBar(
            title = "Daily Practice",
            currentStep = 1,
            totalSteps = 3
        )

        Column(modifier = Modifier.padding(30.dp)) {

            Spacer(modifier = Modifier.height(20.dp))

            IntroHeader()

            Spacer(modifier = Modifier.height(100.dp))

            BreathBoxSectionPreview(  modifier = Modifier.align(Alignment.CenterHorizontally))   // 샘플 박스 (4,4,4)

            Spacer(modifier = Modifier.height(200.dp))

            StartButton(onStart)
        }
    }
}


@Composable
fun IntroHeader() {
    Text("1 / 3", color = Color.White, fontSize = 14.sp)
    Spacer(modifier = Modifier.height(10.dp))

    Text("Breathe and Focus", color = Color.White, fontSize = 22.sp)

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(40.dp)
                .background(Color.Gray.copy(alpha = 0.5f))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                "70% of your voice control comes from breathing.",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                "This step sharpens your diaphragm.",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun BreathBoxSectionPreview( modifier :Modifier = Modifier) {
    Box(
        modifier = Modifier

            .background(color = Gray, RoundedCornerShape(8.dp))
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {


         Row(  horizontalArrangement = Arrangement.spacedBy(20.dp),
             verticalAlignment = Alignment.CenterVertically
         ){ BreathBox("Inhale", "4")
             BreathBox("Hold", "4")
             BreathBox("Exhale", "4")

         }


    }
}


@Composable
fun StartButton(onStart: () -> Unit) {
    Button(
        onClick = onStart,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0088))
    ) {
        Text("Start")
    }
}
@Composable
fun NextButton(onNext: () -> Unit) {
    Button(
        onClick = onNext,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0088))
    ) {
        Text("Next")
    }
}

@Composable
fun FinishButton(onFinish: () -> Unit) {
    Button(
        onClick = onFinish,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0088))
    ) {
        Text("Finish")
    }
}



@Composable
fun BreathBox(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            label,
            color = Color.White,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                value,
                color = DarkBackground,
                fontSize = 36.sp
            )
        }
    }
}


@Composable
fun BreathCountScreen(onFinish: () -> Unit) {

    val labels = listOf("Inhale", "Hold", "Exhale")

    var stepIndex by remember { mutableStateOf(0) }
    var count by remember { mutableStateOf(4) }


    LaunchedEffect(stepIndex, count) {
        if (count > 0) {
            delay(1000)
            count--
        } else {
            if (stepIndex < 2) {
                stepIndex++
                count = 4
            } else {

                onFinish()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {


        TopBar(
            title = "Daily Practice",
            currentStep = 1,
            totalSteps = 3
        )

        Column(modifier = Modifier.padding(30.dp)) {

            Spacer(modifier = Modifier.height(20.dp))


            IntroHeader()

            Spacer(modifier = Modifier.height(100.dp))



            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .background(color = Gray, RoundedCornerShape(8.dp))
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    labels.forEachIndexed { index, label ->

                        val isActive = index == stepIndex

                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    color = if (isActive) Color(0xFFFF0088) else Color.White,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isActive) count.toString() else "4",
                                fontSize = 36.sp,
                                color = if (isActive) Color.White else DarkBackground
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun TonePitchScreen(onNext: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        TopBar(
            title = "Daily Practice",
            currentStep = 2,
            totalSteps = 3
        )
        Column(modifier = Modifier.padding(30.dp)) {

            Spacer(modifier = Modifier.height(20.dp))

            Text("2 / 3", color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))

            Text("Tone and Pitch", color = Color.White, fontSize = 22.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(Color.Gray.copy(alpha = 0.5f))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        "Warm up your voice,",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        "master core melody and pitch accuracy",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(400.dp))
            NextButton(onNext)
        }
    }
}

@Composable
fun PronunciationScreen(onFinish: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        TopBar(
            title = "Daily Practice",
            currentStep = 3,
            totalSteps = 3
        )
        Column(modifier = Modifier.padding(30.dp)) {

            Spacer(modifier = Modifier.height(20.dp))

            Text("3 / 3", color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))

            Text("K-Pronunciation", color = Color.White, fontSize = 22.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(Color.Gray.copy(alpha = 0.5f))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        "correct difficult Korean sounds",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        "and practice the natural folw(intonation).",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(400.dp))
            FinishButton(onFinish)
        }
    }
}

