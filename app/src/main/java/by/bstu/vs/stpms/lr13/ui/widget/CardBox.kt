package by.bstu.vs.stpms.lr13.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.bstu.vs.stpms.lr13.ui.values.itemElevation
import by.bstu.vs.stpms.lr13.ui.values.itemHeight
import by.bstu.vs.stpms.lr13.ui.values.paddingSize

@Composable
fun CardBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    padding: Dp = paddingSize,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        elevation = itemElevation,
        backgroundColor = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = itemHeight)
                .padding(padding),
            content = content
        )
    }

}