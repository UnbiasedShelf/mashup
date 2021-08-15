package by.bstu.vs.stpms.lr13.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.ui.values.largeFontSize
import by.bstu.vs.stpms.lr13.ui.values.mediumFontSize
import by.bstu.vs.stpms.lr13.ui.values.paddingSize

@Composable
fun NoPermissionWidget(onButtonClicked: () -> Unit) {
    CardBox(
        modifier = Modifier.padding(all = paddingSize)
    ) {
        Column {
            Text(
                text = stringResource(R.string.location_permission),
                fontWeight = FontWeight.Bold,
                fontSize = largeFontSize
            )
            Divider(
                thickness = 1.dp,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.location_permission_info),
                fontSize = mediumFontSize
            )
            Button(
                onClick = onButtonClicked,
                modifier = Modifier.padding(top = paddingSize)
            ) {
                Text(text = stringResource(R.string.request_permission))
            }
        }
    }
}