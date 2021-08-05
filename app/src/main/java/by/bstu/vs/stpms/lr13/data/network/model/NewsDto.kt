package by.bstu.vs.stpms.lr13.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.*


class NewsDto {
    @SerializedName("articles")
    var articles: List<Article>? = null

}

class Article {
    @SerializedName("title")
    var title: String? = null
    @SerializedName("url")
    var link: String? = null
    @SerializedName("publishedAt")
    var publishedAt: Date? = null
    @SerializedName("urlToImage")
    var imageUrl: String? = null
    //TODO remove
//    companion object {
//        @JvmStatic
//        @BindingAdapter("android:text")
//        fun bindDate(textView: TextView, date: Date?) {
//            val dateFormat = SimpleDateFormat("dd.MM.YYYY HH:mm");
//            textView.text = date?.let { dateFormat.format(date) }
//        }
//    }
}