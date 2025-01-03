package com.example.tmobileproject.ui

import SkeletonLoader
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tmobileproject.data.remote.model.CardX


@Composable
fun HomeFeedScreen(
    viewModel: HomeFeedViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    if (state.showLoading) {
        SkeletonLoader()
    } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding( vertical = 24.dp, horizontal = 20.dp)
            ) {
                state.homeFeedPage.cards.forEach { card ->
                    item {
                        when (card.cardType) {
                            "text" -> TextCard(card.card)
                            "title_description" -> TitleDescriptionCard(card.card)
                            "image_title_description" -> ImageTitleDescriptionCard(card.card)
                        }
                    }
                }
            }

    }
}

@Composable
fun TextCard(card: CardX) {
    val value = card.value ?: ""
    val textColor = card.description?.attributes?.textColor ?: "#000000"
    val fontSize = card.description?.attributes?.font?.size?.sp ?: 16.sp

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
    ) {
        Text(
            text = value,
            style = TextStyle(
                color = Color(android.graphics.Color.parseColor(textColor)),
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            ),

        )
    }


}

@Composable
fun TitleDescriptionCard(card: CardX) {
    val title = card.title?.value ?: ""
    val titleTextColor = card.title?.attributes?.textColor ?: "#000000"
    val titleFontSize = card.title?.attributes?.font?.size?.sp ?: 18.sp

    val description = card.description?.value ?: ""
    val descriptionTextColor = card.description?.attributes?.textColor ?: "#000000"
    val descriptionFontSize = card.description?.attributes?.font?.size?.sp ?: 14.sp

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        if (title == "Check out our App every week for exciting offers.") {
            HeaderView(title, description, titleTextColor, titleFontSize, descriptionTextColor, descriptionFontSize)
        } else {
            TitleSection(title, description, titleTextColor, titleFontSize, descriptionTextColor, descriptionFontSize)
        }
    }
}

@Composable
fun HeaderView(
    title: String,
    description: String,
    titleTextColor: String,
    titleFontSize: TextUnit,
    descriptionTextColor: String,
    descriptionFontSize: TextUnit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = Color(android.graphics.Color.parseColor(titleTextColor)),
                    fontSize = titleFontSize,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = TextStyle(
                    color = Color(android.graphics.Color.parseColor(descriptionTextColor)),
                    fontSize = descriptionFontSize
                )
            )
        }
    }
}

@Composable
fun TitleSection(
    title: String,
    description: String,
    titleTextColor: String,
    titleFontSize: TextUnit,
    descriptionTextColor: String,
    descriptionFontSize: TextUnit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Color(android.graphics.Color.parseColor(titleTextColor)),
                fontSize = titleFontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = TextStyle(
                color = Color(android.graphics.Color.parseColor(descriptionTextColor)),
                fontSize = descriptionFontSize
            )
        )
    }
}



@Composable
fun ImageTitleDescriptionCard(card: CardX) {
    val imageUrl = card.image?.url ?: ""
    val imageWidth = card.image?.size?.width ?: 1170
    val imageHeight = card.image?.size?.height ?: 1170

    val title = card.title?.value ?: ""
    val titleTextColor = card.title?.attributes?.textColor ?: "#FFFFFF"
    val titleFontSize = card.title?.attributes?.font?.size?.sp ?: 18.sp

    val description = card.description?.value ?: ""
    val descriptionTextColor = card.description?.attributes?.textColor ?: "#FFFFFF"
    val descriptionFontSize = card.description?.attributes?.font?.size?.sp ?: 12.sp

    Box(modifier = Modifier.fillMaxWidth()) {
        // Image with rounded corners and padding
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    (imageHeight.toFloat() / imageWidth.toFloat() * LocalConfiguration.current.screenWidthDp.dp).coerceAtLeast(200.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .shadow(8.dp),
            contentScale = ContentScale.Crop,
            onError = { Log.e("HomeFeedScreen", "Failed to load image: ${it.result}") }
        )

        // Text overlay with a gradient for readability
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 0f,
                        endY = 500f
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = Color(android.graphics.Color.parseColor(titleTextColor)),
                    fontSize = titleFontSize,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = TextStyle(
                        color = Color(android.graphics.Color.parseColor(descriptionTextColor)),
                        fontSize = descriptionFontSize
                    )
                )
            }
        }
    }
}
