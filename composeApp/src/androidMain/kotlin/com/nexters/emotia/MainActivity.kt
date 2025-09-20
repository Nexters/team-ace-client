package com.nexters.emotia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.nexters.emotia.core.platform.AndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // 상태 바 아이콘 색상 반전
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            false

        // Android Context 초기화
        AndroidContext.init(this)

        setContent {
            EmotiaApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    EmotiaApp()
}
