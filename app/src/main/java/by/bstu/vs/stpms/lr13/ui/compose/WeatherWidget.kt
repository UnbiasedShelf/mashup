package by.bstu.vs.stpms.lr13.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import by.bstu.vs.stpms.lr13.ui.theme.MashupTheme
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun WeatherWidget(
    weather: Weather?
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .placeholder(
                visible = weather == null,
                highlight = PlaceholderHighlight.fade(),
                color = MaterialTheme.colors.secondary
            ),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Box(modifier = Modifier
            .height(200.dp)
            .padding(16.dp)
        ) {
            Column {
                Text(
                    text = weather?.city ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = weather?.description ?: "",
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .offset(x = (-20).dp)
                ) {
                    Image(
                        painter = rememberImagePainter(weather?.icon),
                        contentDescription = "Weather icon",
                        modifier = Modifier
                            .size(150.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 60.sp
                                )
                            ) {
                                //Spaces used to make placeholder visible
                                append(weather?.temperature ?: " ".repeat(6))
                            }
                            withStyle(style = SpanStyle(
                                baselineShift = BaselineShift(+2f),
                                fontSize = 20.sp
                            )
                            ) {
                                append(weather?.temperatureUnits ?: "")
                            }
                        },
                        modifier = Modifier
                            .offset(x = (-20).dp)
                    )
                    Column(
                        Modifier.fillMaxWidth()
                    ) {

                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_humidity),
                                contentDescription = "humidity",
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = weather?.humidity ?: "",
                                fontSize = 20.sp,
                            )
                        }

                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_wind),
                                contentDescription = "wind",
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = weather?.windSpeed ?: "",
                                fontSize = 20.sp
                            )
                        }

                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_arrow_right_alt_24),
                                contentDescription = "direction",
                                modifier = Modifier.rotate(-(weather?.windDirectionDegrees?.toFloat() ?: 0.0f))
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = weather?.windDirection ?: "",
                                fontSize = 20.sp
                            )
                        }
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
            windSpeed = "2 mph",
            windDirectionDegrees = 0,
            icon = ""
        ))
    }
}