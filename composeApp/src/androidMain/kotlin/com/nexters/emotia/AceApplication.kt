package com.nexters.emotia

import android.app.Application
import com.nexters.ace.di.initKoin
import org.koin.android.ext.koin.androidContext

class AceApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@AceApplication)
        }
    }
}