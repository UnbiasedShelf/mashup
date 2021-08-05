package by.bstu.vs.stpms.lr13.data.network

import by.bstu.vs.stpms.lr13.data.network.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNews {
    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): NewsDto
}