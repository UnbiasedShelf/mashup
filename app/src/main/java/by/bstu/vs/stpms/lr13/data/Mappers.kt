package by.bstu.vs.stpms.lr13.data

import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.model.MeasureUnits
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.network.NetworkService
import by.bstu.vs.stpms.lr13.data.network.model.NewsDto
import by.bstu.vs.stpms.lr13.data.network.model.WeatherDto
import by.bstu.vs.stpms.lr13.data.util.round
import kotlin.math.roundToInt

fun WeatherDto.toWeather(units: MeasureUnits): Weather {
    return Weather(
        city = name,
        description = weather.first().description,
        temperature = main.temperature.toDouble().roundToInt().toString(),
        temperatureUnits = units.temperatureUnits,
        humidity = "${main.humidity}%",
        windSpeed = "${wind.speed.toDouble().round(1)} ${units.speedUnits}",
        windDirectionDegrees = wind.deg.toDouble().roundToInt(),
        icon = this.weather.first().icon.let { NetworkService.getImageUrlByCode(it) }
    )
}

fun NewsDto.toArticleList(): List<Article>? {
    return this.articles?.map {
        Article(
            title = it.title,
            link = it.link,
            publishedAt = it.publishedAt,
            imageUrl = it.imageUrl
        )
    }
}
