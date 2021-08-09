package by.bstu.vs.stpms.lr13.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class NewsDto(
    @SerializedName("articles")
    val articles: List<ArticleDto>
)

data class ArticleDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val link: String,
    @SerializedName("publishedAt")
    val publishedAt: Date,
    @SerializedName("urlToImage")
    val imageUrl: String?,
)