package com.xiangxue.news.homefragment.newslist.composables.title

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.auto.service.AutoService
import com.xiangxue.base.compose.composablemanager.IComposableService

@AutoService(IComposableService::class)
class TitleComposable : IComposableService<TitleViewModel> {
    override val content: @Composable (item: TitleViewModel) -> Unit = { item ->
        TitleSection(item = item)
    }
    override val type: String
        get() = TitleViewModel::class.java.name
}

@Composable
fun TitleSection(item: TitleViewModel) {
    Box(modifier = Modifier) {
        Text(
            text = item.title,
            color = if (item.isFirstVisible.value) Color.Red else Color.Black,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(15.dp)
        )
    }
    val pxValue = with(LocalDensity.current) { 1.dp.toPx() }

    Spacer(
        Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .height((1 / pxValue).dp)
    )

    LaunchedEffect(key1 = item.isResumed.value, block = {
        Log.d("TitleSection", "TitleComposable isResumed:${item.isResumed.value}")
    })
}
