package io.github.chethann.easy.datastore

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import java.io.File

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
        context = context,
        path = { mContext ->
            if (path != null) {
                return@createDataStore "$path/$preferenceFileName"
            } else if (mContext == null) {
                throw IllegalStateException("Either path or context is mandatory for android, both can't be null")
            } else {
                return@createDataStore File((mContext as Context).filesDir, "datastore/$preferenceFileName").path
            }
        }
    )
}