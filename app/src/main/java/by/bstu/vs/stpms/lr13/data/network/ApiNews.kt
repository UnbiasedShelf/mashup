package by.bstu.vs.stpms.lr13.data.network

import by.bstu.vs.stpms.lr13.data.network.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface that provides methods for news API
 */
interface ApiNews {
    /**
     * Returns [NewsDto] from API
     * @param apiKey The application key for news API
     * @param country The country code for news
     * @return [NewsDto]
     */
    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): NewsDto
}