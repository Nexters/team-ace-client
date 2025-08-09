package com.nexters.emotia

import platform.UIKit.UIDevice
import platform.Foundation.NSUUID

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    
    override fun generateDeviceUuid(): String {
        return NSUUID().UUIDString()
    }
}

actual fun getPlatform(): Platform = IOSPlatform()
