package org.rocketserverkmm.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform