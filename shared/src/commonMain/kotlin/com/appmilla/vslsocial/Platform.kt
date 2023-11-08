package com.appmilla.vslsocial

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform