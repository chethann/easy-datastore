package io.github.chethann.easy.datastore

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    val testDataStore = remember { TestDataStore() }
    Screen(testDataStore)
}