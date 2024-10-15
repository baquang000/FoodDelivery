package com.example.fooddelivery.components.charts

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.CustomBar(
    size: Dp,
    max: Float,
    value: Float,
) {
    var height by remember { mutableStateOf(0.dp) }
    val heightStateAnimate by animateDpAsState(
        targetValue = height,
        tween(durationMillis = 2000, delayMillis = 300, easing = LinearEasing), label = ""
    )

    LaunchedEffect(key1 = size) {
        height = size
    }

    Box(
        modifier = Modifier
            .padding(start = 4.dp, bottom = 0.dp, end = 4.dp, top = 4.dp)
            .size(heightStateAnimate)
            .weight(1f)
            .border(BorderStroke(1.dp, color = Color.Black))
            .background(Color.Red.copy(alpha = heightStateAnimate.value / max)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = value.toString(), color = Color.White, fontSize = 12.sp)
    }
}