package io.github.chethann.easy.datastore

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        val testDataStore = remember { TestDataStore(path = "${System.getProperty("user.home")}/Library/Application Support/sdsadsad") }
        Screen(testDataStore)
    }
}