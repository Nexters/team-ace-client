package com.nexters.emotia

import androidx.compose.ui.window.ComposeUIViewController
import com.nexters.emotia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { AceApp() }
