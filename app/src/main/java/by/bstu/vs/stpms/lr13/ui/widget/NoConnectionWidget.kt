package by.bstu.vs.stpms.lr13.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bstu.vs.stpms.lr13.ui.values.itemElevation
import by.bstu.vs.stpms.lr13.ui.values.largeFontSize
import by.bstu.vs.stpms.lr13.ui.values.mediumFontSize
import by.bstu.vs.stpms.lr13.ui.values.paddingSize

@Composable
fun NoConnectionWidget(onTryAgainButtonClicked: () -> Unit) {
    CardBox(
        modifier = Modifier
            .padding(start = paddingSize, end = paddingSize, top = paddingSize),
    ) {
        Column {
            Text(
                //TODO localize (and other places also)
                text = "No connection!",
                fontWeight = FontWeight.Bold,
                fontSize = largeFontSize
            )
            Divider(
                thickness = 1.dp,
                color = Color.Black
            )
            Text(
                //TODO localize (and other places also)
                text = "This application requires network connection for receiving recent news and current weather. Please, enable network connection and try again.",
                fontSize = mediumFontSize
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onTryAgainButtonClicked) {
                Text(text = "Try again")
            }
        }
    }
}