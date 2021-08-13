package by.bstu.vs.stpms.lr13.ui.values.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MashupTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        content = content
    )
}

private val LightColors = lightColors(
    primary = Blue700,
    primaryVariant = Blue700,
    onPrimary = Color.White,
    secondary = Blue800,
    secondaryVariant = Blue800,
    onSecondary = Color.White
)