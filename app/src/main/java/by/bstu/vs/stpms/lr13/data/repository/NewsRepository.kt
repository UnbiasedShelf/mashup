package by.bstu.vs.stpms.lr13.data.repository

import android.util.Log
import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.network.ApiNews
import by.bstu.vs.stpms.lr13.data.network.NetworkService
import by.bstu.vs.stpms.lr13.data.network.model.NewsDto
import by.bstu.vs.stpms.lr13.data.toArticleList

/**
 * Repository of news
 */
class NewsRepository {
    val TAG = "NewsRepository"

    val newsApi: ApiNews = NetworkService.newsService()

    /**
     * Receives [NewsDto] from [ApiNews] and maps it to list of [Article]
     * @param appId The application key for news API
     * @param country The country code for news
     * @return list of [Article]
     */
    suspend fun getNews(appId: String, country: String): List<Article> {
        val newsDto = newsApi.getNews(
            apiKey = appId,
            country = country
        )
        val articles = newsDto.toArticleList()
        Log.i(TAG, "getNews: return ${articles.size} for country $country")
        return articles
    }
}