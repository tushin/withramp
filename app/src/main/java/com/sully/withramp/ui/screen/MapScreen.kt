package com.sully.withramp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    var showModal by remember {
        mutableStateOf(false)
    }
    var selectedRoute by remember {
        mutableStateOf<Route?>(null)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        selectedRoute?.let {
            MapWithRoute(it)
        }

        FilledIconButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd),
            onClick = { showModal = true }
        ) {
            Icon(imageVector = Icons.Default.Route, contentDescription = null)
        }
    }

    if (showModal) {
        ModalBottomSheet(onDismissRequest = { showModal = false }) {
            Column(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                listOf(Route.route1, Route.route2).forEach { route ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                selectedRoute = route
                                showModal = false
                            })
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        text = route.name
                    )
                }
            }
        }
    }
}

@Composable
fun MapWithRoute(route: Route) {
    val name = route.name
    val path = route.path
    val cameraPositionState = rememberCameraPositionState {}
    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {

            Polyline(
                points = path,
                width = 8f,
                color = Color.Magenta
            )
            Marker(
                state = remember { MarkerState(position = path.first()) },
                alpha = 0.8f,
                title = "출발",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            )
            Marker(
                state = remember { MarkerState(position = path.last()) },
                alpha = 0.8f,
                title = "도착",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
            LaunchedEffect(path) {
                val bounds = LatLngBounds.builder().apply {
                    path.forEach { include(it) }
                }.build()

                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngBounds(bounds, 200)
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.5f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = name,
            style = MaterialTheme.typography.titleMedium,
        )


    }

}

class Route(
    val name: String,
    val path: List<LatLng>
) {
    companion object {
        val route1 = Route(
            name = "서울대 - 서울대입구역",
            path = listOf(
                LatLng(37.4662944, 126.9481644),
                LatLng(37.4663537, 126.9481346),
                LatLng(37.4666276, 126.9480952),
                LatLng(37.4666276, 126.9480952),
                LatLng(37.4667300, 126.9484213),
                LatLng(37.4667557, 126.9485036),
                LatLng(37.4668895, 126.9487989),
                LatLng(37.4669778, 126.9489815),
                LatLng(37.4672825, 126.9495560),
                LatLng(37.4681215, 126.9510765),
                LatLng(37.4682921, 126.9513353),
                LatLng(37.4685096, 126.9515780),
                LatLng(37.4689321, 126.9519074),
                LatLng(37.4689755, 126.9519421),
                LatLng(37.4697141, 126.9522161),
                LatLng(37.4699524, 126.9523025),
                LatLng(37.4720201, 126.9530712),
                LatLng(37.4724596, 126.9531777),
                LatLng(37.4726706, 126.9532021),
                LatLng(37.4728320, 126.9532123),
                LatLng(37.4731503, 126.9532315),
                LatLng(37.4735001, 126.9532459),
                LatLng(37.4735911, 126.9532395),
                LatLng(37.4739586, 126.9531962),
                LatLng(37.4741441, 126.9531643),
                LatLng(37.4744909, 126.9530961),
                LatLng(37.4744909, 126.9530961),
                LatLng(37.4745647, 126.9530809),
                LatLng(37.4745647, 126.9530809),
                LatLng(37.4746502, 126.9530611),
                LatLng(37.4751599, 126.9529544),
                LatLng(37.4757318, 126.9528485),
                LatLng(37.4757840, 126.9528379),
                LatLng(37.4767971, 126.9526247),
                LatLng(37.4772077, 126.9525493),
                LatLng(37.4774923, 126.9524975),
                LatLng(37.4777337, 126.9524663),
                LatLng(37.4781068, 126.9524500),
                LatLng(37.4781979, 126.9524539),
                LatLng(37.4785703, 126.9524839),
                LatLng(37.4787001, 126.9524988),
                LatLng(37.4788453, 126.9525125),
                LatLng(37.4788949, 126.9525234),
                LatLng(37.4792395, 126.9525809),
                LatLng(37.4796157, 126.9526482),
                LatLng(37.4800207, 126.9527188),
                LatLng(37.4801660, 126.9527392),
                LatLng(37.4802625, 126.9527600),
                LatLng(37.4804492, 126.9527881),
                LatLng(37.4807415, 126.9528346),
                LatLng(37.4807993, 126.9528647),
                LatLng(37.4807993, 126.9528647),
                LatLng(37.4808164, 126.9528646),
            )
        )
        val route2 = Route(
            name = "판교역 - 삼평동행정복지센터",
            path = listOf(
                LatLng(37.3948837, 127.1113869),
                LatLng(37.3948837, 127.1113654),
                LatLng(37.3954407, 127.1113682),
                LatLng(37.3959275, 127.1113691),
                LatLng(37.3959275, 127.1113691),
                LatLng(37.3959335, 127.1118164),
                LatLng(37.3959607, 127.1118773),
                LatLng(37.3959933, 127.1119133),
                LatLng(37.3960556, 127.1119695),
                LatLng(37.3960982, 127.1120348),
                LatLng(37.3960981, 127.1122652),
                LatLng(37.3960950, 127.1126991),
                LatLng(37.3960950, 127.1126991),
                LatLng(37.3962248, 127.1127029),
                LatLng(37.3963753, 127.1127022),
                LatLng(37.3963799, 127.1127146),
                LatLng(37.3963853, 127.1127281),
                LatLng(37.3963962, 127.1127360),
                LatLng(37.3964070, 127.1127415),
                LatLng(37.3964064, 127.1128274),
                LatLng(37.3964054, 127.1130748),
                LatLng(37.3964003, 127.1131573),
                LatLng(37.3964003, 127.1131573),
                LatLng(37.3966454, 127.1131595),
                LatLng(37.3979506, 127.1131437),
                LatLng(37.3980722, 127.1131386),
                LatLng(37.3981732, 127.1131335),
                LatLng(37.3982012, 127.1131447),
                LatLng(37.3982220, 127.1131627),
                LatLng(37.3982391, 127.1131818),
                LatLng(37.3982591, 127.1132111),
                LatLng(37.3982591, 127.1132111),
                LatLng(37.3983348, 127.1132118),
                LatLng(37.3984105, 127.1132137),
                LatLng(37.3984105, 127.1132137),
                LatLng(37.3984203, 127.1131865),
                LatLng(37.3984329, 127.1131661),
                LatLng(37.3984554, 127.1131513),
                LatLng(37.3984806, 127.1131467),
                LatLng(37.3985067, 127.1131465),
                LatLng(37.3993108, 127.1131492),
                LatLng(37.3994180, 127.1131531),
                LatLng(37.3995361, 127.1131537),
                LatLng(37.3996849, 127.1131551),
                LatLng(37.3997200, 127.1131629),
                LatLng(37.3997417, 127.1131763),
                LatLng(37.3997616, 127.1131988),
                LatLng(37.3997752, 127.1132281),
                LatLng(37.3997752, 127.1132281),
                LatLng(37.3998708, 127.1132265),
                LatLng(37.3999636, 127.1132226),
                LatLng(37.3999636, 127.1132226),
                LatLng(37.3999717, 127.1132023),
                LatLng(37.3999824, 127.1131853),
                LatLng(37.4000004, 127.1131750),
                LatLng(37.4000157, 127.1131693),
                LatLng(37.4002041, 127.1131717),
                LatLng(37.4015995, 127.1132029),
                LatLng(37.4016725, 127.1132003),
                LatLng(37.4028894, 127.1132110),
                LatLng(37.4029399, 127.1132107),
                LatLng(37.4029715, 127.1132151),
                LatLng(37.4029940, 127.1132251),
                LatLng(37.4029940, 127.1132251),
                LatLng(37.4030384, 127.1132803),
                LatLng(37.4030719, 127.1133389),
                LatLng(37.4031015, 127.1135715),
                LatLng(37.4031720, 127.1139078),
                LatLng(37.4032769, 127.1142745),
                LatLng(37.4033224, 127.1144121),
                LatLng(37.4034306, 127.1146906),
                LatLng(37.4037355, 127.1153161),
                LatLng(37.4037355, 127.1153161),
                LatLng(37.4037981, 127.1154492),
                LatLng(37.4037981, 127.1154492),
                LatLng(37.4039925, 127.1158990),
                LatLng(37.4041358, 127.1161819),
                LatLng(37.4041358, 127.1161819),
                LatLng(37.4045207, 127.1161844),
                LatLng(37.4045207, 127.1161844),
                LatLng(37.4046896, 127.1160005),
                LatLng(37.4047893, 127.1159130),
                LatLng(37.4049701, 127.1157754),
                LatLng(37.4051815, 127.1156579),
                LatLng(37.4052950, 127.1156313),
                LatLng(37.4053562, 127.1156096),
                LatLng(37.4057953, 127.1156356),
                LatLng(37.4057953, 127.1156356),
                LatLng(37.4058457, 127.1156330),
            )
        )
    }
}




