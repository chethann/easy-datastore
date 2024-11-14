package io.github.chethann.easy.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AbstractPreferenceDataStore(
    private val prefName: String,
    private val path: String? = null,
    private val context: Any? = null,
    private val corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    private val migrations: List<DataMigration<Preferences>> = listOf(),
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
) : PreferenceApis {

    private val dataStore by lazy {
        preferenceDataStore(
            preferenceFileName = prefName.toPrefName(),
            coroutineScope = coroutineScope,
            context = context,
            path = path,
            migrations = migrations,
            corruptionHandler = corruptionHandler
        )
    }

    override fun <T> getAsync(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[key]
        }
    }

    override fun <T> getAsync(key: Preferences.Key<T>, default: T): Flow<T> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[key] ?: default
        }
    }

    override suspend fun <T> get(key: Preferences.Key<T>): T? {
        return dataStore.data.firstOrNull()?.get(key)
    }

    override suspend fun <T> get(key: Preferences.Key<T>, default: T): T {
        return get(key) ?: default
    }

    override fun <T> setAsync(key: Preferences.Key<T>, value: T?) {
        coroutineScope.launch {
            dataStore.edit {
                set(key, value)
            }
        }
    }

    override suspend fun <T> set(key: Preferences.Key<T>, value: T?) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                if (value == null) {
                    preferences.remove(key)
                } else {
                    preferences[key] = value
                }
            }
        }
    }

    override suspend fun <T> remove(key: Preferences.Key<T>) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences.remove(key)
            }
        }
    }

    override suspend fun clearData() {
        withContext(Dispatchers.IO) {
            dataStore.edit { it.clear() }
        }
    }

    private fun String.toPrefName() = "$this.preferences_pb"
}

expect fun preferenceDataStore(
    preferenceFileName: String,
    context: Any?,
    path: String?,
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
    coroutineScope: CoroutineScope,
    migrations: List<DataMigration<Preferences>>,
): DataStore<Preferences>
