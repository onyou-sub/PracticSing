plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.practicsing"
    compileSdk = 36 // 최신 버전 유지

    defaultConfig {
        applicationId = "com.example.practicsing"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ⚠️ Version Catalog 오류 회피: libs. 대신 직접 문자열 지정 또는 최신 버전 사용
    


    // Core Android & Kotlin
    implementation("androidx.core:core-ktx:1.13.1") // libs.androidx.core.ktx 대체
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0") // libs.androidx.lifecycle.runtime.ktx 대체
    implementation(libs.androidx.core.ktx)
    implementation(platform("com.google.firebase:firebase-bom:34.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-firestore")
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.animation.core)
    implementation(libs.androidx.compose.foundation.layout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")

    val composeBom = platform("androidx.compose:compose-bom:2024.04.00") // 최신 BOM 사용
    implementation(composeBom)

    // Core Compose Dependencies (libs.androidx.* 대체)
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation") // 👈 foundation Unresolved reference 해결

    // ⭐ 1. Navigation 문제 해결 (NavHost, composable)
    // 최신 권장 버전 (2.9.6) 사용
    implementation("androidx.navigation:navigation-compose:2.7.5") // 2.7.5로 설정되어 있던 버전 그대로 유지.

    // ⭐ 2. ViewModel 문제 해결 (viewModel() 함수)
    // 최신 권장 버전 (2.9.4) 사용
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // ⭐ 3. 아이콘 문제 해결 (RecordVoiceOver)
    // 최신 권장 버전 (1.7.8) 사용
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // 테스트 의존성
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // UI Tooling (Preview) - 프리뷰 화면 렌더링을 위해 필요
    debugImplementation("androidx.compose.ui:ui-tooling-preview")

    // 선택 사항: Live Literal 및 기타 디버깅 기능을 위해 필요
    debugImplementation("androidx.compose.ui:ui-tooling")

    //유튜브 영상 가져오기
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")

}