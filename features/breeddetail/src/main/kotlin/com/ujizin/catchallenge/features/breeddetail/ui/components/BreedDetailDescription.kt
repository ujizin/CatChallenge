package com.ujizin.catchallenge.features.breeddetail.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.ujizin.catchallenge.core.ui.R
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme

@Composable
fun BreedDetailTopic(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(4.dp))
        Text(description)
    }
}

@Preview(showBackground = true)
@Composable
private fun BreedDetailDescriptionPreview(
    @PreviewParameter(LoremIpsum::class) loremIpsum: String,
) {
    CatChallengeTheme {
        BreedDetailTopic(
            title = stringResource(R.string.description_label),
            description = loremIpsum,
        )
    }

}