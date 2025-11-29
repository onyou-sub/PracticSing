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
import androidx.compose.material3.Text
import com.example.practicsing.ui.common.RoundedBackButton


@Composable
fun SongPracticeScreen(
    songId: String,
    navController: NavHostController,
    repo: SongRepositoryImpl = SongRepositoryImpl()
) {

    val song = remember {
        repo.getSongs().firstOrNull { it.id == songId }
    }


    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf( Color(
                        0xFFFF0088),
                        Color(0xFF0E0E0E) ) ) ),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(150.dp))

        Text(
            text="practice Mode",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White

        )
        Spacer(modifier = Modifier.height(50.dp))

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
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = song.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

        } else {
            Text("No information.")
            Spacer(modifier = Modifier.height(32.dp))
        }



        Spacer(modifier = Modifier.height(40.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Button(
                onClick = {
                    navController.navigate("SongPlay/$songId")
                },
                modifier = Modifier
                    .height(80.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF0088),
                    contentColor = Color.White
                )

            ) {
                Text(
                    "Full Song",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            OutlinedButton(
                onClick = {
                    navController.navigate("PartPlay/$songId" )
                          },
                modifier = Modifier
                    .height(80.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Part only",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                navController.navigate("pronunciation_test")
            },
            modifier = Modifier
                .height(50.dp)
                .width(300.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF0088),
                contentColor = Color.White
            )

        ) {
            Text(
                "Test button",
                style = MaterialTheme.typography.bodyMedium,
            )
        }




    }

    Box(modifier = Modifier.fillMaxSize()) {
        RoundedBackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            onClick = { navController.popBackStack() }
        )
    }
}


