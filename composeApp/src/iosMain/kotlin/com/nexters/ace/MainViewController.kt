package com.nexters.ace

import androidx.compose.ui.window.ComposeUIViewController
import com.nexters.ace.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { AceApp() }
