package com.sully.withramp.ui.screen

import android.R.attr.name
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import com.sully.withramp.ui.theme.WithRampTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        CardInput()
        UserList()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserList() {
    val coroutineScope = rememberCoroutineScope()

    var users by remember {
        mutableStateOf<List<User>>(emptyList())
    }

    val options = listOf("Red", "Blue", "Green")
    var selectedOption by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(selectedOption) {
        users = if (selectedOption != null) {
            Firebase.firestore
                .collection("users")
                .whereEqualTo("color", selectedOption)
                .get().await()
                .toObjects<User>()
        } else {
            Firebase.firestore
                .collection("users")
                .get().await()
                .toObjects<User>()
        }
    }

    Column {
        Row {
            Button(
                onClick = { selectedOption = null }
            ) {
                Text("All")
            }

            options.forEach { option ->
                Button(
                    onClick = { selectedOption = option }
                ) {
                    Text(option)
                }
            }
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            users.forEach {
                OutlinedCard {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(it.name)
                        Text(it.color)
                    }
                }
            }

        }
    }
}

data class User(
    val name: String = "Unknown",
    val color: String = "Red",
)

@Composable
private fun CardInput() {
    var name by remember { mutableStateOf("") }
    val options = listOf("Red", "Blue", "Green")
    var selectedOption by remember { mutableStateOf(options[0]) }
    val coroutineScope = rememberCoroutineScope()

    OutlinedCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { text -> name = text },
                placeholder = { Text("사용자 이름") }
            )
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                options.forEachIndexed { index, option ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = { selectedOption = option },
                        selected = option == selectedOption,
                        label = { Text(option) }
                    )
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                enabled = name.isNotBlank(),
                onClick = {
                    coroutineScope.launch {
                        Firebase.firestore.collection("users").document(name)
                            .set(User(name, selectedOption)).await()
                        selectedOption = options[0]
                        name = ""
                    }
                }
            ) {
                Text("추가하기")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    WithRampTheme {
        ProfileScreen()
    }
}

// https://firebase.google.com/docs/firestore/quickstart?hl=ko&_gl=1*vfmqqu*_up*MQ..*_ga*MjAzOTYzMzQxMC4xNzYzNjg0Njc1*_ga_CW55HF8NVT*czE3NjM2ODQ2NzQkbzEkZzAkdDE3NjM2ODQ2NzQkajYwJGwwJGgw#android
