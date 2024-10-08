package com.ujizin.catchallenge.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme

@Composable
fun CenterLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun CenterLoadingPreview() {
    CatChallengeTheme {
        CenterLoading(Modifier.fillMaxSize())
    }
}