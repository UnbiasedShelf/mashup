package by.bstu.vs.stpms.lr13.data.repository

import android.util.Log
import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.network.ApiNews
import by.bstu.vs.stpms.lr13.data.network.NetworkService
import by.bstu.vs.stpms.lr13.data.toArticleList

class NewsRepository {
    val TAG = "NewsRepository"
    //TODO di
    val newsApi: ApiNews = NetworkService.newsService()

    suspend fun getNews(appId: String, country: String): List<Article> {
        val newsDto = newsApi.getNews(
            apiKey = appId,
            country = country
        )
        val articles = newsDto.toArticleList() ?: listOf()
        Log.i(TAG, "getNews: return $articles")
        return articles
    }
}