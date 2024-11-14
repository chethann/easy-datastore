package io.github.chethann.easy.datastore

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

class TestDataStore(
    context: Any? = null,
    path: String? = null
): AbstractPreferenceDataStore(PREFERENCE_NAME, context = context, path = path) {

    fun getTestString(): Flow<String?> {
        return getAsync(TEST_STRING_KEY)
    }

    suspend fun setTestString(value: String) {
        set(TEST_STRING_KEY, value)
    }

    companion object {
        private const val PREFERENCE_NAME = "testPreference"
        private val TEST_STRING_KEY = stringPreferencesKey("TEST_STRING")
    }
}