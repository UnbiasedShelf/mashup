package by.bstu.vs.stpms.lr13.data

import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.model.Weather
import by.bstu.vs.stpms.lr13.data.network.model.NewsDto
import by.bstu.vs.stpms.lr13.data.network.model.WeatherDto

fun WeatherDto.toWeather(): Weather {
    return Weather(
        description = this.description?.first()?.description,
        temperature = this.main?.temperature?.toDoubleOrNull(),
        pressure = this.main?.pressure?.toDoubleOrNull(),
        humidity = this.main?.humidity?.toDoubleOrNull(),
        windSpeed = this.wind?.speed?.toDoubleOrNull(),
        windDirection = this.wind?.deg?.toDoubleOrNull(),
        icon = this.description?.first()?.icon
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