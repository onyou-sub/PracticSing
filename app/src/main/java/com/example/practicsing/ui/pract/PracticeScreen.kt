package com.example.practicsing.ui.pract

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.navigation.TopBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
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
import java.io.File
import kotlin.math.abs

import com.example.practicsing.data.PracticePrefs
import kotlinx.coroutines.*
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
    val context = LocalContext.current
    var step by remember { mutableStateOf(PracticeStep.Splash) }

    val currentDay = remember { PracticePrefs.getCurrentDay(context) }


    when (step) {

        PracticeStep.Splash -> StartScreen(
            day = currentDay,
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

                PracticePrefs.updateLastPracticeDate(context)


                if (PracticePrefs.canIncreaseDay(context)) {
                    PracticePrefs.increaseDay(context)
                }

                CoroutineScope(Dispatchers.Main).launch {
                    delay(50)
                    navController.navigate("home") {
                        popUpTo("practice") { inclusive = true }
                    }
                }
            }
        )
    }
}


@Composable
fun StartScreen(day: Int, onNext: () -> Unit) {

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
                Text("Day $day")
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

            Spacer(modifier = Modifier.height(100.dp))

            if (!started) {
                StartButton(onStart = { started = true })
            }
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
fun PitchIndicator(notePitch: NotePitch?, targetNoteName: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = if (notePitch != null)
                "${notePitch.name}${notePitch.octave} ${notePitch.frequency.format(1)}"
            else "No note detected",
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        val tolerance = 0.03f // ±3%
        val isInTargetRange = notePitch != null &&
                abs(notePitch.frequency - notePitch.targetHz) / notePitch.targetHz <= tolerance

        val deviation = if (notePitch != null) {
            (notePitch.frequency - notePitch.targetHz) / notePitch.targetHz
        } else 0f

        val maxOffset = 150.dp
        val clampedDeviation = deviation.coerceIn(-0.2f, 0.2f)
        val animatedOffset by animateFloatAsState(
            targetValue = clampedDeviation * maxOffset.value,
            animationSpec = tween(durationMillis = 100)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.DarkGray)
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(Color.Green)
                    .align(Alignment.Center)
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width((abs(animatedOffset) + 4).dp)
                    .background(
                        if (isInTargetRange) Color.Green.copy(alpha = 0.5f)
                        else if (deviation > 0) Color.Red.copy(alpha = 0.5f)
                        else Color.Blue.copy(alpha = 0.5f)
                    )
                    .align(Alignment.Center)
                    .offset(x = if (deviation > 0) 0.dp else (-abs(animatedOffset)).dp)
            )

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(if (isInTargetRange) Color.Green else Color.Red)
                    .align(Alignment.Center)
                    .offset(x = animatedOffset.dp)
            )
        }
    }
}

@Composable
fun TonePitchScreen(
    onNext: () -> Unit,
    testAudioResId: Int = R.raw.testfile // default test file
) {
    var notePitch by remember { mutableStateOf<NotePitch?>(null) }
    val context = LocalContext.current

    // PitchDetector instance
    val pitchDetector = PitchDetector(sampleRate = 44100) { pitchInHz ->
        notePitch = processPitch(pitchInHz)
    }

    // Permission launcher for microphone
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // Do nothing; start on button press
        }

    // Request permission on first launch
    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    // Cleanup
    DisposableEffect(Unit) {
        onDispose { pitchDetector.stop() }
    }

    // Notes and state
    val noteFrequencies = remember { mapOf("A" to 440.0f, "C" to 261.63f, "E" to 329.63f) }
    val targetNotes = remember { noteFrequencies.keys.toList() }
    var currentNoteIndex by remember { mutableStateOf(0) }
    var countdown by remember { mutableStateOf(5) }
    var practiceActive by remember { mutableStateOf(false) }
    var useTestFile by remember { mutableStateOf(false) }

    // Core practice logic
    LaunchedEffect(practiceActive, currentNoteIndex) {
        if (practiceActive) {
            var sustainedTime = 0
            val requiredSustainedTime = 5000 // 5 seconds
            countdown = 5
            while (sustainedTime < requiredSustainedTime) {
                delay(100)
                val isCorrectNote = notePitch?.name == targetNotes[currentNoteIndex]
                if (isCorrectNote) {
                    sustainedTime += 100
                    countdown = kotlin.math.ceil((requiredSustainedTime - sustainedTime) / 1000f).toInt().coerceAtLeast(0)
                } else {
                    sustainedTime = 0
                    countdown = 5
                }
            }

            if (currentNoteIndex < targetNotes.size - 1) {
                currentNoteIndex++
            } else {
                practiceActive = false
                pitchDetector.stop()
                onNext()
            }
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        TopBar(title = "Daily Practice", currentStep = 2, totalSteps = 3)
        Column(
            modifier = Modifier.padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            IntroHeader(
                stepText = "2 / 3",
                title = "Tone and Pitch",
                description1 = "Warm up your voice,",
                description2 = "master core melody and pitch accuracy."
            )
            Spacer(modifier = Modifier.height(30.dp))

            val currentTargetNote = targetNotes.getOrNull(currentNoteIndex)
            if (practiceActive) {
                Text("Sing: $currentTargetNote", color = Color.White, fontSize = 28.sp)
                Spacer(Modifier.height(8.dp))
                Text("Hold for $countdown seconds", color = Color.Gray, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(20.dp))
            } else {
                Text("Get ready to sing A, C, then E.", color = Color.White, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Hold each note for 5 seconds.", color = Color.Gray, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(20.dp))
            }

            PitchIndicator(
                notePitch = notePitch.takeIf { practiceActive },
                targetNoteName = if (practiceActive) currentTargetNote else null
            )

            Spacer(modifier = Modifier.weight(1f))

            if (practiceActive) {
                Button(
                    onClick = {
                        pitchDetector.stop()
                        practiceActive = false
                        currentNoteIndex = 0
                        countdown = 5
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PinkAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Stop")
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (useTestFile) {
                                val audioBuffer = loadWavFromRaw(context, testAudioResId)
                                pitchDetector.startFromBuffer(audioBuffer)
                                practiceActive = true
                                currentNoteIndex = 0
                            } else {
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.RECORD_AUDIO
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    pitchDetector.start()
                                    practiceActive = true
                                    currentNoteIndex = 0
                                } else {
                                    requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PinkAccent),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Start")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

// Toggle button to switch between test file and mic
                    Button(
                        onClick = { useTestFile = !useTestFile },
                        colors = ButtonDefaults.buttonColors(containerColor = Gray),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (useTestFile) "Using Test File" else "Using Microphone")
                    }
                }
            }
            NextButton(onNext)
        }
    }
}

// Utility to load WAV from res/raw
fun loadWavFromRaw(context: Context, resId: Int): FloatArray {
    val inputStream = context.resources.openRawResource(resId)
    val header = ByteArray(44)
    inputStream.read(header) // skip WAV header

    val bytes = inputStream.readBytes()
    val samples = bytes.size / 4 // 4 bytes per stereo sample (2 bytes per channel)
    val floats = FloatArray(samples)

    for (i in 0 until samples) {
        val leftLow = bytes[i * 4].toInt() and 0xFF
        val leftHigh = bytes[i * 4 + 1].toInt()
        val rightLow = bytes[i * 4 + 2].toInt() and 0xFF
        val rightHigh = bytes[i * 4 + 3].toInt()

        val leftSample = ((leftHigh shl 8) or leftLow).toShort()
        val rightSample = ((rightHigh shl 8) or rightLow).toShort()

        // average both channels to mono
        floats[i] = ((leftSample + rightSample) / 2f) / 32768f
    }

    return floats
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
            // to fix this error ,use actual phone ?
            YouTubePlayer(videoId = "aqz-KE-bpKQ")

            Spacer(modifier = Modifier.height(100.dp))
            FinishButton(onFinish)
        }
    }
}
