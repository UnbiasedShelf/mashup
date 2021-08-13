package by.bstu.vs.stpms.lr13.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.ui.values.iconSpace
import by.bstu.vs.stpms.lr13.ui.values.largeFontSize
import by.bstu.vs.stpms.lr13.ui.values.mediumFontSize
import by.bstu.vs.stpms.lr13.ui.values.paddingSize
import by.bstu.vs.stpms.lr13.ui.values.theme.MashupTheme
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun WeatherWidget(
    weather: Weather?
) {
    CardBox(
        modifier = Modifier
            .padding(horizontal = paddingSize)
            .fillMaxWidth()
            .placeholder(
                visible = weather == null,
                highlight = PlaceholderHighlight.fade(),
                color = MaterialTheme.colors.primary
            ),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column {
            Text(
                text = weather?.city ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = largeFontSize
            )
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = weather?.description ?: "",
                fontSize = mediumFontSize,
                fontStyle = FontStyle.Italic
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = rememberImagePainter(weather?.icon),
                    contentDescription = "Weather icon",
                    modifier = Modifier
                        .height(150.dp)
                        .width(100.dp),
                    contentScale = ContentScale.FillHeight
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 60.sp
                            )
                        ) {
                            append(weather?.temperature ?: "")
                        }
                        withStyle(style = SpanStyle(
                            baselineShift = BaselineShift(+2f),
                            fontSize = mediumFontSize
                        )
                        ) {
                            append(weather?.temperatureUnits ?: "")
                        }
                    },
                )
                Column(Modifier.padding(start = paddingSize)) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_humidity),
                            contentDescription = "humidity",
                        )
                        Spacer(modifier = Modifier.width(iconSpace))
                        Text(
                            text = weather?.humidity ?: "",
                            fontSize = mediumFontSize,
                        )
                    }

                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_wind),
                            contentDescription = "wind",
                        )
                        Spacer(modifier = Modifier.width(iconSpace))
                        Text(
                            text = weather?.windSpeed ?: "",
                            fontSize = mediumFontSize,
                        )
                    }

                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_right_alt_24),
                            contentDescription = "direction",
                            modifier = Modifier.rotate(-(weather?.windDirectionDegrees?.toFloat() ?: 0.0f))
                        )
                        Spacer(modifier = Modifier.width(iconSpace))
                        Text(
                            text = weather?.windDirection ?: "",
                            fontSize = mediumFontSize
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WeatherPreview() {
    MashupTheme {
        WeatherWidget(weather = Weather(
            city = "Los Angeles",
            description = "Clear",
            temperature = "92",
            temperatureUnits = "°F",
            humidity = "40%",
            windSpeed = "14.0 mph",
            windDirectionDegrees = 0,
            icon = ""
        ))
    }
}