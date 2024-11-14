package io.github.chethann.easy.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferenceApis {

    fun <T> getAsync(key: Preferences.Key<T>): Flow<T?>

    fun <T> getAsync(key: Preferences.Key<T>, default: T): Flow<T>

    suspend fun <T> get(key: Preferences.Key<T>): T?

    suspend fun <T> get(key: Preferences.Key<T>, default: T): T

    fun <T> setAsync(key: Preferences.Key<T>, value: T?)

    suspend fun <T> set(key: Preferences.Key<T>, value: T?)

    suspend fun <T> remove(key: Preferences.Key<T>)

    suspend fun clearData()
}