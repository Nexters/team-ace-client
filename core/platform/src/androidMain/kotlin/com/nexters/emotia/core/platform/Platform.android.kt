package com.nexters.emotia.core.platform

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import java.util.UUID

object AndroidContext {
    lateinit var context: Context
        private set

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun isInitialized(): Boolean {
        return ::context.isInitialized
    }
}

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    @SuppressLint("HardwareIds")
    override fun generateDeviceUuid(): String {
        // Android에서는 ANDROID_ID를 사용하여 기기별 고유 식별자를 생성
        return try {
            if (AndroidContext.isInitialized()) {
                Settings.Secure.getString(
                    AndroidContext.context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                    ?: UUID.randomUUID().toString() // fallback으로 랜덤 UUID 사용
            } else {
                UUID.randomUUID().toString() // context가 초기화되지 않은 경우 fallback
            }
        } catch (e: Exception) {
            UUID.randomUUID().toString() // 예외 발생 시 fallback
        }
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()