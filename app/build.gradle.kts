plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.practicsing"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.practicsing"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "ETRI_KEY", "\"${properties["ETRI_KEY"]}\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // ------------------------------------------------------
    // ⭐ Compose BOM (핵심)
    // ------------------------------------------------------
    val composeBom = platform("androidx.compose:compose-bom:2024.10.00")

    implementation(composeBom)

    // (선택) unit test에서도 Compose 쓰면
    testImplementation(composeBom)

    // androidTest에서도 BOM 같이 사용
    androidTestImplementation(composeBom)

    // Compose (버전 자동 관리됨)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")

    // Material3
    implementation("androidx.compose.material3:material3")

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.9.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // ViewModel for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Extended Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Coil – 이미지 로딩
    implementation("io.coil-kt:coil-compose:2.4.0")

    // ------------------------------------------------------
    // Firebase
    // ------------------------------------------------------
    implementation(platform("com.google.firebase:firebase-bom:34.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")

    // ------------------------------------------------------
    // Etc
    // ------------------------------------------------------
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ink.brush)

    // ------------------------------------------------------
    // Test
    // ------------------------------------------------------
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // ------------------------------------------------------
    // Debug (Preview 툴링)
    // ------------------------------------------------------
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // 무료 노래 재생
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    // 발음평가
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation ("com.android.support.constraint:constraint-layout:1.0.2")
    implementation( "commons-lang:commons-lang:2.6")


}
