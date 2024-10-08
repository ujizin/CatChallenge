package com.ujizin.catchallenge.feature.breeddetail.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.ujizin.catchallenge.core.ui.R
import com.ujizin.catchallenge.core.ui.model.BreedUI
import com.ujizin.catchallenge.core.ui.preview.BreedParameterProvider
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme

@Composable
fun BreedDetailContainer(
    name: String,
    description: String,
    temperament: String,
    origin: String,
    isFavorite: Boolean,
    onFavoriteChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        BreedDetailHeader(
            modifier = Modifier.fillMaxWidth(),
            title = name,
            origin = origin,
            temperament = temperament,
            isFavorite = isFavorite,
            onFavoriteChanged = onFavoriteChanged
        )
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        BreedDetailTopic(
            title = stringResource(R.string.description_label),
            description = description,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BreedDetailContainerPreview(
    @PreviewParameter(BreedParameterProvider::class) breed: BreedUI,
) {
    CatChallengeTheme {
        var isFavorite by remember { mutableStateOf(breed.isFavorite) }
        BreedDetailContainer(
            modifier = Modifier.padding(16.dp),
            name = breed.name,
            description = breed.description,
            temperament = breed.temperament,
            origin = breed.origin,
            isFavorite = isFavorite,
            onFavoriteChanged = { isFavorite = it },
        )
    }
}