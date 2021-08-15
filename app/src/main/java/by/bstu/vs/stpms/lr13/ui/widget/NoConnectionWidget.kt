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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.ui.values.itemElevation
import by.bstu.vs.stpms.lr13.ui.values.largeFontSize
import by.bstu.vs.stpms.lr13.ui.values.mediumFontSize
import by.bstu.vs.stpms.lr13.ui.values.paddingSize

@Composable
fun NoConnectionWidget(onTryAgainButtonClicked: () -> Unit) {
    CardBox(
        modifier = Modifier
            .padding(start = paddingSize, end = paddingSize, top = paddingSize),
        isGrowable = true
    ) {
        Column {
            Text(
                text = stringResource(R.string.no_connection),
                fontWeight = FontWeight.Bold,
                fontSize = largeFontSize
            )
            Divider(
                thickness = 1.dp,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.no_connection_info),
                fontSize = mediumFontSize
            )
            Button(
                onClick = onTryAgainButtonClicked,
                modifier = Modifier.padding(top = paddingSize)
            ) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}