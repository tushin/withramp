package com.sully.withramp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sully.withramp.ui.theme.WithRampTheme

@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Layout",
            style = MaterialTheme.typography.titleLarge
        )

        Text("row")
        Row {
            RedBox()
            BlueCircle()
            BlackTextCard()
        }

        Text("column")
        Column {
            RedBox()
            BlueCircle()
            BlackTextCard()
        }

        Text("box")
        Box(
            contentAlignment = Alignment.Center
        ) {
            BlackTextCard()
            RedBox()
            BlueCircle()
            // 나중에 기술한 것이 상단에 위치함
        }

        Text("box - aligned")
        Box {
            BlackTextCard()
            RedBox(
                modifier = Modifier.align(Alignment.TopEnd)
            )
            BlueCircle(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            // 나중에 기술한 것이 상단에 위치함
        }



    }
}

@Composable
fun RedBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .background(Color.Red)
    )
}

@Composable
fun BlueCircle(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(Color.Blue)
    )
}

@Composable
fun BlackTextCard(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .size(width = 100.dp, height = 80.dp)
    ) {
        Text(
            text = "black",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }}

@Preview(showBackground = true)
@Composable
private fun SettingScreenPreview() {
    WithRampTheme {
        SettingScreen()
    }
}