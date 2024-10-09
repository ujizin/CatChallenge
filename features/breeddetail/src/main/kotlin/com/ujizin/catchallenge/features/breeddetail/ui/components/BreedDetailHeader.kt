package com.ujizin.catchallenge.features.breeddetail.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ujizin.catchallenge.core.ui.R
import com.ujizin.catchallenge.core.ui.components.FavoriteButton
import com.ujizin.catchallenge.core.ui.theme.CatChallengeThemeForPreview


@Composable
fun BreedDetailHeader(
    title: String,
    isFavorite: Boolean,
    origin: String,
    temperament: String,
    onFavoriteChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1F)) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = stringResource(R.string.origin_label, origin),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            FavoriteButton(
                isFavorite = isFavorite,
                onFavoriteChanged = onFavoriteChanged,
            )
        }
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.temperament_label, temperament),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BreedDetailHeaderPreview() {
    CatChallengeThemeForPreview {
        var isFavorite by remember { mutableStateOf(false) }
        BreedDetailHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = "Title",
            origin = "Portugal",
            temperament = "Curious, Intelligent, Interactive, Lively, Playful, Social",
            isFavorite = isFavorite,
            onFavoriteChanged = { isFavorite = it }
        )
    }
}