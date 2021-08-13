package by.bstu.vs.stpms.lr13.ui.widget

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import by.bstu.vs.stpms.lr13.R
import by.bstu.vs.stpms.lr13.data.model.Article
import by.bstu.vs.stpms.lr13.data.util.articleFormat
import by.bstu.vs.stpms.lr13.ui.values.mediumFontSize
import by.bstu.vs.stpms.lr13.ui.values.paddingSize
import by.bstu.vs.stpms.lr13.ui.values.theme.MashupTheme
import coil.compose.rememberImagePainter
import java.util.*


@Composable
fun ArticleWidget(article: Article) {
    val context = LocalContext.current
    CardBox(
        modifier = Modifier
            .padding(horizontal = paddingSize)
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.link)
                startActivity(context, intent, null)
            },
        padding = 0.dp
    ) {
        //TODO placeholder if image == null
        Image(
            painter = rememberImagePainter(
                data = article.imageUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "Article image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.75f))
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = article.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = mediumFontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.publishedAt.articleFormat(),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.End)
            )
        }
    }
}

@Preview
@Composable
fun ArticlePreview() {
    MashupTheme {
        ArticleWidget(
            article = Article(
                title = "Some not interesting news blah blah blah",
                link = "not needed in preview",
                publishedAt = Date(),
                imageUrl = "not needed in preview"
            )
        )
    }
}