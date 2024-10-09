package com.ujizin.catchallenge.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme
import com.ujizin.catchallenge.core.ui.theme.CatChallengeThemeForPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier.background(color = MaterialTheme.colorScheme.background)) {
        TopAppBar(
            navigationIcon = navigationIcon,
            title = title,
        )
        content()
    }
}

@Preview
@Composable
private fun FixedAppBarPreview(@PreviewParameter(LoremIpsum::class) loremIpsum: String) {
    CatChallengeThemeForPreview {
        FixedAppBar(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            title = { Text("Title") },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            },
        ) {
            Text(loremIpsum)
        }
    }
}