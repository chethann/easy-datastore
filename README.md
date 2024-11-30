A wrapper around Multiplatform Datastore to expose easy to use API to store and retrieve key value pairs.

Usage

```kotlin
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
```

Example usage of TestDataStore in a composable

```kotlin
@Composable
fun Screen(testDataStore: TestDataStore) {
    var preferenceValue by remember { mutableStateOf<String?>(null) }
    val coroutineScope = remember {
        CoroutineScope(Dispatchers.IO)
    }

    LaunchedEffect(Unit) {
        testDataStore.getTestString().collect {
            preferenceValue = it
        }
    }

    Column {
        var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = {
                text = it
            }
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            coroutineScope.launch { testDataStore.setTestString(text) }
        }) {
            Text("Save")
        }

        Spacer(Modifier.height(8.dp))

        Text("Stored value: $preferenceValue")

    }
}
```

You can specify the data store file location using, path variable

```kotlin
        val testDataStore = remember { TestDataStore(path = "${System.getProperty("user.home")}/Library/Application Support/sdsadsad") }
``` 

Note: Either context or path in AbstractPreferenceDataStore is mandatory in Android. An exception is thrown is both are not set.   