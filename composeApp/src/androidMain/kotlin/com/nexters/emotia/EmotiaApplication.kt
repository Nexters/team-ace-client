package com.nexters.emotia

import android.app.Application
import com.nexters.emotia.di.initKoin
import org.koin.android.ext.koin.androidContext

class EmotiaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@EmotiaApplication)
        }
    }
}