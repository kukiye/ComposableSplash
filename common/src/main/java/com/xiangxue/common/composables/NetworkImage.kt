package com.xiangxue.common.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop) {
    Image(
        painter = rememberGlidePainter(
            url,
            fadeIn = true
        ),
        contentDescription = "",
        modifier = modifier,
        contentScale = contentScale
    )
}