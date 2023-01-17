// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
@file:OptIn(ExperimentalComposeUiApi::class)

import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlin.random.Random


const val WIDTH = 1000
const val HEIGHT = 700

@Composable
@Preview
fun App() {
    var isUserFarming by remember { mutableStateOf(false) }
    var isUserFarmed by remember { mutableStateOf(false) }
    var noButtonCoordinates by remember { mutableStateOf(IntOffset(400, 300)) }
    var noButtonSize by remember { mutableStateOf(IntSize(0, 0)) }

    MaterialTheme {
        Box(modifier= Modifier.fillMaxSize()){

            Text("farm social credit? \uD83D\uDE0F", modifier = Modifier.offset(385.dp, 275.dp))
            Button(onClick = {
                             isUserFarmed = true
            }, modifier = Modifier
                .offset { noButtonCoordinates }
                .pointerMoveFilter(onEnter = {
                    val x = Random.nextInt(0, WIDTH - noButtonSize.width - 50)
                    val y = Random.nextInt(0, HEIGHT - noButtonSize.height - 50)

                    isUserFarmed = false
                    isUserFarming = false
                    noButtonCoordinates = IntOffset(x, y)

                    false
                }).onGloballyPositioned {
                    noButtonSize = it.size
                }
            ) {Text("YES")}

            Button(onClick = { isUserFarming = true; isUserFarmed = false }, modifier = Modifier.offset(470.dp, 300.dp)) {Text("NO")}

            if(isUserFarming)
            Text("-69,420 social credit \uD83D\uDE31 \uD83D\uDCC9", modifier = Modifier.offset(385.dp, 350.dp))
            if(isUserFarmed)
                Text("+300 social credit \uD83D\uDC72 \uD83C\uDDE8\uD83C\uDDF3 \uD83D\uDCC8", modifier = Modifier.offset(385.dp, 350.dp))

        }

    }
}

fun main() = application {
    val state = WindowState(size = DpSize(WIDTH.dp, HEIGHT.dp))
    Window(onCloseRequest = ::exitApplication, state = state, resizable = false ) {
        App()
    }
}
