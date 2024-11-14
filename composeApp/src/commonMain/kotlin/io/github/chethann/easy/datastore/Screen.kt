package io.github.chethann.easy.datastore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

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