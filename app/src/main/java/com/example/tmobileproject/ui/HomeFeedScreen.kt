package com.example.tmobileproject.ui

import SkeletonLoader
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                state.homeFeedPage.cards?.forEach { card ->
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
}

@Composable
fun ShowProgressBar() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(45.dp),
        )
    }
}

@Composable
fun TextCard(card: CardX) {
    val value = card.value ?: ""  // Fallback to empty string if value is null
    val textColor = card.description?.attributes?.textColor ?: "#000000"  // Fallback color if null
    val fontSize =
        card.description?.attributes?.font?.size?.sp ?: 16.sp  // Fallback font size if null

    Text(
        text = value,
        style = TextStyle(
            color = Color(android.graphics.Color.parseColor(textColor)),
            fontSize = fontSize
        ),
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun TitleDescriptionCard(card: CardX) {
    val title = card.title?.value ?: ""  // Fallback to empty string if title is null
    val titleTextColor = card.title?.attributes?.textColor ?: "#000000"  // Fallback color if null
    val titleFontSize =
        card.title?.attributes?.font?.size?.sp ?: 16.sp  // Fallback font size if null

    val description =
        card.description?.value ?: ""  // Fallback to empty string if description is null
    val descriptionTextColor =
        card.description?.attributes?.textColor ?: "#000000"  // Fallback color if null
    val descriptionFontSize =
        card.description?.attributes?.font?.size?.sp ?: 14.sp  // Fallback font size if null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Color(android.graphics.Color.parseColor(titleTextColor)),
                fontSize = titleFontSize
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
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
    val imageUrl = card.image?.url ?: ""  // Fallback if image is null
    val imageWidth = card.image?.size?.width ?: 1170  // Default width if null
    val imageHeight = card.image?.size?.height ?: 1170  // Default height if null

    val title = card.title?.value ?: ""  // Fallback if title is null
    val titleTextColor = card.title?.attributes?.textColor ?: "#FFFFFF"  // Fallback color if null
    val titleFontSize =
        card.title?.attributes?.font?.size?.sp ?: 18.sp  // Fallback font size if null

    val description = card.description?.value ?: ""  // Fallback if description is null
    val descriptionTextColor =
        card.description?.attributes?.textColor ?: "#FFFFFF"  // Fallback color if null
    val descriptionFontSize =
        card.description?.attributes?.font?.size?.sp ?: 12.sp  // Fallback font size if null

    Box(modifier = Modifier.fillMaxWidth()) {
        // Image

        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    (imageHeight.toFloat() / imageWidth.toFloat() * LocalConfiguration.current.screenWidthDp.dp).coerceAtLeast(
                        200.dp
                    )
                )
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            onError = { Log.e("HomeFeedScreen", "Failed to load image: ${it.result}") }
        )

        // Text overlay
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = Color(android.graphics.Color.parseColor(titleTextColor)),
                    fontSize = titleFontSize
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
