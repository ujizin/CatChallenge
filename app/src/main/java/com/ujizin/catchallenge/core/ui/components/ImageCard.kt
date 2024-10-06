package com.ujizin.catchallenge.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ujizin.catchallenge.R
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme
import com.ujizin.catchallenge.core.ui.utils.dataImageSuffixFormat

@Composable
fun ImageCard(
    name: String,
    imageUrl: String?,
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onFavoriteChanged: (Boolean) -> Unit = {},
    @DrawableRes fallbackImageRes: Int = R.drawable.ic_cat,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .aspectRatio(1F),
                model = ImageRequest.Builder(LocalContext.current)
                    .dataImageSuffixFormat(imageUrl)
                    .fallback(fallbackImageRes)
                    .error(fallbackImageRes)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = name
            )
            CardRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                name = name,
                isFavorite = isFavorite,
                onFavoriteChanged = onFavoriteChanged,
            )
        }
    }
}


@Composable
private fun CardRow(
    name: String,
    isFavorite: Boolean,
    onFavoriteChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .weight(1F, fill = true)
                .padding(16.dp),
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        FavoriteButton(
            isFavorite = isFavorite,
            onFavoriteChanged = onFavoriteChanged,
        )
    }
}


@Preview
@Composable
private fun BreedCardPreview() {
    CatChallengeTheme {
        var favorite by remember { mutableStateOf(false) }
        ImageCard(
            modifier = Modifier.width(256.dp),
            name = "Dragon Li",
            imageUrl = "imageUrl",
            isFavorite = favorite,
            onFavoriteChanged = { favorite = it },
            onClick = {}
        )
    }
}