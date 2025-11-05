package com.sully.withramp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.sully.withramp.ui.screen.HomeScreen
import com.sully.withramp.ui.screen.MapScreen
import com.sully.withramp.ui.screen.ProfileScreen
import com.sully.withramp.ui.screen.SettingScreen
import com.sully.withramp.ui.theme.WithRampTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WithRampTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember {
        mutableIntStateOf(0)
    }
    val state = rememberPagerState(initialPage = 0, pageCount = { 4 })

    LaunchedEffect(selectedTab) {
        state.animateScrollToPage(selectedTab)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                MainTab.entries.forEachIndexed { index,  tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            state = state,
            userScrollEnabled = false,
            beyondViewportPageCount = 4,
        ) { index ->
            when (index) {
                0 -> HomeScreen()
                1 -> MapScreen()
                2 -> ProfileScreen()
                3 -> SettingScreen()
            }
        }
    }
}

enum class MainTab(
    val label: String,
    val icon: ImageVector,
) {
    Home("Home", Icons.Filled.Home),
    Map("Map", Icons.Filled.LocationOn),
    Profile("Profile", Icons.Filled.Person),
    Setting("Setting", Icons.Filled.Settings)
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WithRampTheme {
        MainScreen()
    }
}