package com.ujizin.catchallenge.feature.favorites.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ujizin.catchallenge.core.ui.R
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme

@Composable
fun FavoriteEmptyContainer(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.favorite_empty_description),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FavoriteEmptyContainerPreview() {
    CatChallengeTheme {
        FavoriteEmptyContainer(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
        )
    }
}