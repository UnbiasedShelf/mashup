package by.bstu.vs.stpms.lr13.data.model

import java.util.*

/**
 * Data class that contains info about article
 * @property title The title of article
 * @property link The URL to web-page of article
 * @property publishedAt The time when article was published
 * @property imageUrl The URL to image of article
 */
data class Article(
    val title: String,
    val link: String,
    val publishedAt: Date,
    val imageUrl: String,
)