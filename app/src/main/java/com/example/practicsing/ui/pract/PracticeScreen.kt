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
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
//영상 가져오기 위한 import
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.*
import androidx.activity.ComponentActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.*
import com.example.practicsing.R

// -----------------------------
//  Practice Step Enum
// -----------------------------
enum class PracticeStep {
    Splash,
    BreathIntro,

    TonePitch,
    Pronunciation,
    Finish

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



        PracticeStep.BreathIntro -> BreathPracticeScreen(
            onFinish = {step = PracticeStep.TonePitch

            }
        )
        PracticeStep.TonePitch -> TonePitchScreen(
            onNext = { step = PracticeStep.Pronunciation}
        )
        PracticeStep.Pronunciation  -> PronunciationScreen(
            onFinish = { step = PracticeStep.Finish }
        )
        PracticeStep.Finish -> FinishScreen(
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
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(100.dp)
            )

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
fun FinishScreen(onFinish: () -> Unit){
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
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(100.dp)
            )

            Text("Done for today!", color = Color.White, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(20.dp))


        }
    }
}



@Composable
fun IntroHeader(
    stepText: String,
    title: String,
    description1: String,
    description2: String
) {
    Text(stepText, color = Color.White, fontSize = 14.sp)
    Spacer(modifier = Modifier.height(10.dp))

    Text(title, color = Color.White, fontSize = 22.sp)
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
            Text(description1, color = Color.Gray, fontSize = 14.sp)
            Text(description2, color = Color.Gray, fontSize = 14.sp)
        }
    }
}



@Composable
fun BreathPracticeScreen(onFinish: () -> Unit) {

    val labels = listOf("Inhale", "Hold", "Exhale")

    var started by remember { mutableStateOf(false) }
    var stepIndex by remember { mutableStateOf(0) }
    var count by remember { mutableStateOf(4) }

    // Count down logic
    LaunchedEffect(started, stepIndex, count) {
        if (started) {
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

            IntroHeader(
                stepText = "1 / 3",
                title = "Breathe and Focus",
                description1 = "70% of your voice control comes from breathing.",
                description2 = "This step sharpens your diaphragm."
            )

            Spacer(modifier = Modifier.height(80.dp))



            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {


                Row(
                    horizontalArrangement = Arrangement.spacedBy(70.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    labels.forEach { label ->
                        Text(
                            text = label,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))


                Box(
                    modifier = Modifier
                        .background(Gray, RoundedCornerShape(16.dp))
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        labels.forEachIndexed { index, _ ->

                            val isActive = started && index == stepIndex
                            val displayValue = if (isActive) count.toString() else "4"

                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(
                                        color = if (isActive) PinkAccent else Color.White,
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = displayValue,
                                    fontSize = 36.sp,
                                    color = if (isActive) Color.White else DarkBackground
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(250.dp))

            if (!started) {
                StartButton(onStart = { started = true })
            }
        }
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
fun StartButton(onStart: () -> Unit) {
    Button(
        onClick = onStart,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = PinkAccent)
    ) {
        Text("Start")
    }
}
@Composable
fun NextButton(onNext: () -> Unit) {
    Button(
        onClick = onNext,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = PinkAccent)
    ) {
        Text("Next")
    }
}

@Composable
fun FinishButton(onFinish: () -> Unit) {
    Button(
        onClick = onFinish,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = PinkAccent)
    ) {
        Text("Finish")
    }
}






@Composable
fun YouTubePlayer(videoId: String) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        factory = { context ->
            val view = YouTubePlayerView(context).apply {


                (context as ComponentActivity).lifecycle.addObserver(this)

            }

            view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(player: YouTubePlayer) {
                    player.loadVideo(videoId, 0f)
                }
            })

            view
        }
    )
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

            IntroHeader(
                stepText = "2 / 3",
                title = "Tone and Pitch",
                description1 = "Warm up your voice,",
                description2 = "master core melody and pitch accuracy."
            )

            YouTubePlayer(videoId = "aqz-KE-bpKQ")

            Spacer(modifier = Modifier.height(250.dp))
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

            IntroHeader(
                stepText = "3 / 3",
                title = "K-pronunciation",
                description1 = "Correct difficult Korean sounds,",
                description2 = "and practice the natural flow(intonation)."
            )

            YouTubePlayer(videoId = "aqz-KE-bpKQ")

            Spacer(modifier = Modifier.height(250.dp))
            FinishButton(onFinish)
        }
    }
}

