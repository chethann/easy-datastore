package io.github.chethann.easy.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope

actual fun preferenceDataStore(
    preferenceFileName: String,
    context: Any?,
    path: String?,
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
    coroutineScope: CoroutineScope,
    migrations: List<DataMigration<Preferences>>
): DataStore<Preferences> {
    return createDataStore(
        corruptionHandler = corruptionHandler,
        coroutineScope = coroutineScope,
        migrations = migrations,
        path = {
            if (path != null) {
                return@createDataStore "$path/$preferenceFileName"
            }
            "${getStorageDirectory()}/easyDataStore/$preferenceFileName"
        }
    )
}

private fun getStorageDirectory(): String {
    val os = System.getProperty("os.name").lowercase()
    return when {
        os.contains("win") -> System.getenv("LOCALAPPDATA")
        os.contains("mac") -> "${System.getProperty("user.home")}/Library/Application Support"
        else -> System.getProperty("user.home") + "/.local/share/"
    }
}