package com.nexters.emotia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nexters.emotia.core.platform.AndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

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
