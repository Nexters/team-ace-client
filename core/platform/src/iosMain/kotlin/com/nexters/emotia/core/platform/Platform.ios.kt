package com.nexters.emotia.core.platform

import platform.Foundation.NSUUID
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override fun generateDeviceUuid(): String {
        // iOS에서는 identifierForVendor를 사용하여 앱별 고유 식별자를 생성
        return UIDevice.currentDevice.identifierForVendor?.UUIDString()
            ?: NSUUID().UUIDString() // fallback으로 랜덤 UUID 사용
    }
}

actual fun getPlatform(): Platform = IOSPlatform()