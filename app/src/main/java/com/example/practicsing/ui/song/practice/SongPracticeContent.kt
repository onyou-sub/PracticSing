package com.example.practicsing.ui.song.practice

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.practicsing.data.repository.SongRepositoryImpl
import coil.compose.AsyncImage
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


@Composable
fun SongPracticeContent(
    songId: String,
    navController: NavHostController,
    repo: SongRepositoryImpl = SongRepositoryImpl()
) {
    // songId로 곡 정보 조회
    val song = remember {
        repo.getSongs().firstOrNull { it.id == songId }
    }


    Column(

        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text="practice Mode",
            style = MaterialTheme.typography.titleLarge

        )

        if (song != null) {

            AsyncImage(
                model = song.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = song.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = song.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))
        } else {
            Text("No information.")
            Spacer(modifier = Modifier.height(32.dp))
        }

        Text(
            text = "How to sing?",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(){
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .height(54.dp)
            ) {
                Text("Full Song")
            }

            OutlinedButton(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .height(54.dp)
            ) {
                Text("Part only")
            }
        }




    }
}
