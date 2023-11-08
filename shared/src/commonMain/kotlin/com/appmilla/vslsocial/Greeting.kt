package com.appmilla.vslsocial

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, This is shared Kotlin Multiplatform code running on ${platform.name}!"
    }
}