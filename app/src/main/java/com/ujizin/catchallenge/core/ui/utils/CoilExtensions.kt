package com.ujizin.catchallenge.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import coil.request.ImageRequest

@Composable
fun ImageRequest.Builder.dataImageSuffixFormat(
    imageUrl: String?,
): ImageRequest.Builder = run {
    var format by rememberSaveable { mutableStateOf("jpg") }
    data("$imageUrl.$format")
        .listener(
            onError = { _, _ -> if (format != "png") format = "png" }
        )
}
