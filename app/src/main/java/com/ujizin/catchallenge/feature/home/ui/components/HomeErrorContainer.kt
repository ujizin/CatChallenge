package com.ujizin.catchallenge.feature.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ujizin.catchallenge.R
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme

@Composable
fun HomeErrorContainer(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.error_description))
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = onRetryClick
        ) {
            Text(stringResource(R.string.button_retry_label))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeErrorContainerPreview() {
    CatChallengeTheme {
        HomeErrorContainer(
            modifier = Modifier.fillMaxSize(),
            onRetryClick = {}
        )
    }
}
