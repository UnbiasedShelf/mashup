package by.bstu.vs.stpms.lr13.data.network

import by.bstu.vs.stpms.lr13.data.network.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNews {
    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        //google-news-ru
        @Query("sources") sources: String
    ): NewsDto
}