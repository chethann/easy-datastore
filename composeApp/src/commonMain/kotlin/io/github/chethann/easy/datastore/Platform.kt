package io.github.chethann.easy.datastore

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform