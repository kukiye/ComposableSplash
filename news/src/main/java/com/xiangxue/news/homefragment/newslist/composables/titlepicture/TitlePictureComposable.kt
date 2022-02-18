package com.xiangxue.news.homefragment.newslist.composables.titlepicture

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.auto.service.AutoService
import com.xiangxue.news.R
import com.xiangxue.base.compose.composablemanager.IComposableService
import com.xiangxue.common.composables.NetworkImage

@AutoService(IComposableService::class)
class TitlePictureComposable : IComposableService<TitlePictureViewModel> {
    override val content: @Composable (item: TitlePictureViewModel) -> Unit = { item ->
        TitlePictureSection(item = item)
    }
    override val type: String
        get() = TitlePictureViewModel::class.java.name
}

@Composable
fun TitlePictureSection(item: TitlePictureViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Box {
            NetworkImage(
                url = item.picture,
                modifier = Modifier
                    .requiredHeight(height = 220.dp)
                    .fillMaxWidth(),
            )
            Image(
                painter = painterResource(id = R.mipmap.video_play),
                "",
                modifier = Modifier.align(Alignment.Center)
            )

        }
        Text(
            text = item.title,
            color = if(item.isFirstVisible.value) Color.Red else Color.Black ,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.Start).padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 15.dp)
        )
    }
    val pxValue = with(LocalDensity.current) { 1.dp.toPx() }
    Spacer(
        Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .height((1/pxValue).dp)
    )
}
