// file: com/example/practicsing/main/MainActivity.kt

package com.example.practicsing.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
// ⚠️ 주의: androidx.navigation.NavGraph 임포트는 삭제해야 합니다.
import com.example.practicsing.navigation.BottomNavigationBar
import com.example.practicsing.navigation.AppNavHost // ✅ AppNavHost 임포트
import com.example.practicsing.main.theme.PracticSingTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticSingTheme {
                val navController = rememberNavController()
                Scaffold(
                    // 패딩 파라미터는 자동으로 전달되며, 사용하지 않아도 무방합니다.
                    bottomBar = { BottomNavigationBar(navController) }
                ) { paddingValues -> // ✨ paddingValues를 받아 NavHost에 전달하는 것이 좋습니다.
                    // NavGraph 대신 AppNavHost 함수를 호출합니다.
                    AppNavHost(
                        navController = navController
                    )
                }
            }
        }
    }


    /*

          &&& &&  & &&                                  ,@@@@@@@,
      && &\/&\|& ()|/ @, &&              ,,,.   ,@@@@@@/@@,  .oo8888o.
      &\/(/&/&||/& /_/)_&/_&          ,&%%&%&&%,@@@@@/@@@@@@,8888\88/8o
   &() &\/&|()|/&\/ '%" & ()         ,%&\%&&%&&%,@@@\@@@/@@@88\88888/88'
  &_\_&&_\ |& |&&/&__%_/_& &&        %&&%&%&/%&&%@@\@@/ /@@@88888\88888'
&&   && & &| &| /& & % ()& /&&       %&&%/ %&%%&&@@\ V /@@' `88\8 `/88'
 ()&_---()&\&\|&&-&&--%---()~        `&%\ ` /%&'    |.|        \ '|8'
     &&     \|||                         |o|        | |         | |
             |||                         |.|        | |         |o|
             |||                         | |        | |         | |
       , -=-~  .-^- _             .__ \\/ ._\//_/__/  ,\_//__\\/.  \_//__/_



    */

}