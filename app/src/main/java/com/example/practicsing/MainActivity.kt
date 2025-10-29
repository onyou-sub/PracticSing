package com.example.practicsing

import android.os.Bundle
import android.os.Build
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.practicsing.ui.theme.PracticSingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticSingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationScreen(this)
                }
            }
        }
    }
}

@Composable
fun NotificationScreen(context: Context) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { showNotification(context) }) {
            Text(text = "알림 보내기")
        }
    }
}

fun showNotification(context: Context) {
    val channelId = "MY_channel"
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Android 13 이상 권한 확인
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
    }

    // Android 8.0 up version have to give permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "PracticSing Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "PracticSing 알림 채널입니다."
        }
        notificationManager.createNotificationChannel(channel)
    }

    // alarm creation
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("PracticSing 🎵")
        .setContentText("지금 노래 연습할 시간이에요!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    // alarm 표시
    notificationManager.notify(1002, builder.build())
}
