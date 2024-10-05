package com.ujizin.catchallenge.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ImageCard(
    name: String,
    imageUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .aspectRatio(1F),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .fallback(fallbackImageRes)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = name
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun BreedCardPreview() {
    CatChallengeTheme {
        ImageCard(
            modifier = Modifier.width(256.dp),
            name = "Dragon Li",
            imageUrl = "imageUrl",
            onClick = {}
        )
    }
}