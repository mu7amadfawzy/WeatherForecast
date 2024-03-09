package com.tasks.currentweather.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun LoadingBubblesScreen(
    circleColor: Color = colorScheme.secondary,
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest, properties = properties
    ) {
        val animationDelay = 400
        val initialAlpha = 0.3f

        val circles = listOf(remember {
            Animatable(initialValue = initialAlpha)
        }, remember {
            Animatable(initialValue = initialAlpha)
        }, remember {
            Animatable(initialValue = initialAlpha)
        }).also {
            it.forEachIndexed { index, animator ->
                LaunchedEffect(Unit) {
                    delay(timeMillis = (animationDelay / it.size).toLong() * index)
                    animator.animateTo(
                        targetValue = 1f, animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = animationDelay),
                            repeatMode = RepeatMode.Reverse
                        )
                    )
                }
            }
        }

        Row {
            circles.forEachIndexed { index, animator ->
                if (index != 0) Spacer(modifier = Modifier.width(width = 6.dp))
                Box(
                    modifier = Modifier
                        .size(size = 12.dp)
                        .clip(shape = CircleShape)
                        .background(circleColor.copy(alpha = animator.value))
                ) {}
            }
        }
    }
}