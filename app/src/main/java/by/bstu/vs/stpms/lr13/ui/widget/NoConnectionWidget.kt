package by.bstu.vs.stpms.lr13.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoConnectionWidget(onTryAgainButtonClicked: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Column {
            Text(
                text = "No connection!",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Button(onClick = onTryAgainButtonClicked) {
                Text(text = "Try again")
            }
        }
    }
}