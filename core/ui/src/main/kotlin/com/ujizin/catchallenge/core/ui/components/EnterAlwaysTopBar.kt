package com.ujizin.catchallenge.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterAlwaysTopBar(
    header: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    expandedHeight: Dp = 120.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            MediumTopAppBar(
                windowInsets = WindowInsets(top = 0.dp, right = 16.dp, bottom = 0.dp, left = 0.dp),
                title = {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                        content = header,
                    )
                },
                collapsedHeight = 0.dp,
                expandedHeight = expandedHeight,
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            content = content,
        )
    }
}

@Preview
@Composable
private fun TopAppBarContentPreview() {
    CatChallengeTheme {
        val list = remember {
            List(100) { "Text-$it" }
        }
        EnterAlwaysTopBar(
            modifier = Modifier.fillMaxSize(),
            header = { Text("Title content") },
            content = {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(list) { text ->
                        Text(text = text,)
                    }
                }
            },
        )
    }
}
