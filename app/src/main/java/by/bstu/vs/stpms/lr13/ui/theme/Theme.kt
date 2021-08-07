package by.bstu.vs.stpms.lr13.ui.theme

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
    primary = Blue900,
    primaryVariant = Blue900,
    onPrimary = Color.White,
    secondary = Blue700,
    secondaryVariant = Blue900,
    onSecondary = Color.White
)