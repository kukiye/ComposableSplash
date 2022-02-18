package com.xiangxue.news.homefragment.newslist.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val MAX_OFFSET = 200f
private const val PULL_TO_REFRESH = "下拉刷新"
private const val REFRESHING = "刷新中..."

/**
 * SwipeToRefresh composable function should be used whenever the user can refresh the content
 * of a view via vertical scroll by using [NestedScrollConnection]. It will call [onRefresh] callback method where [isRefreshing] boolean value
 * should be set to true and later false.
 *
 * @param isRefreshing refreshing is in progress or not
 * @param progressBarColor color of the progress bar
 * @param pullToRefreshTextColor color of pull to refresh text
 * @param refreshSectionBackgroundColor color of refresh section where pull to refresh text and refreshing displayed
 * @param onRefresh callback method for refreshing content
 * @param content body to display content
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SwipeToRefresh(
    isRefreshing: Boolean,
    progressBarColor: Color? = null,
    pullToRefreshTextColor: Color? = null,
    refreshSectionBackgroundColor: Color? = null,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val contentComposableOffset = rememberSaveable { mutableStateOf(0f) }
    val refreshState = rememberSaveable { mutableStateOf(isRefreshing) }

    val connection = object : NestedScrollConnection {
        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (source == NestedScrollSource.Drag && !refreshState.value) {
                if (available.y > 0) {
                    contentComposableOffset.value =
                        (contentComposableOffset.value + available.y).coerceAtMost(MAX_OFFSET)
                    if (contentComposableOffset.value >= MAX_OFFSET) {
                        refreshState.value = true
                        onRefresh.invoke()
                    }
                }
            }
            return super.onPostScroll(consumed, available, source)
        }

/*        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            if (refreshState.value) {
                boxOneOffset.value = MID_OFFSET
                return Velocity.Zero
            }

            boxOneOffset.value = offsetSize.value.toFloat()
            dragOffset.value = 0f
            refreshTextValue.value = PULL_TO_REFRESH

            return super.onPostFling(consumed, available)
        }*/
    }

    Box(
        modifier = Modifier
            .nestedScroll(connection)
            .background(color = refreshSectionBackgroundColor ?: Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        RefreshSection(isRefreshing, progressBarColor, pullToRefreshTextColor)
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x = 0, y = contentComposableOffset.value.roundToInt())
                }
                .fillMaxSize()
        ) {
            content.invoke()
        }
    }
    if (!isRefreshing) {
        contentComposableOffset.value = 0f
        refreshState.value = false
    }
}

/**
 * @param isRefreshing boolean to indicate refreshing
 * @param progressBarColor color of the progress bar
 * @param pullToRefreshTextColor color of the pull to refresh text
 */
@Composable
private fun RefreshSection(
    isRefreshing: Boolean,
    progressBarColor: Color? = null,
    pullToRefreshTextColor: Color? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (isRefreshing) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    color = progressBarColor
                        ?: Color.LightGray,
                    strokeWidth = 1.dp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = if (isRefreshing) "$REFRESHING..." else PULL_TO_REFRESH,
                color = pullToRefreshTextColor ?: Color.LightGray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * [BoxState] State of the box either [Collapsed] or [Expanded]

private enum class BoxState {
Collapsed,
Expanded;
}
 */
/**
 * [offsetAnimation] provides the int value based on BoxState to animate [RefreshSection]
 *
 * @param updateTransition stores the state
 * @param update callback method to update the value of [refreshTextValue]
 */

/*
@Composable
private fun offsetAnimation(
    updateTransition: Transition<MutableState<BoxState>>,
    update: () -> Unit
): State<Int> {
    return updateTransition.animateInt(
        transitionSpec = {
            when (this.initialState.value) {
                BoxState.Expanded -> tween(0)
                BoxState.Collapsed -> {
                    update.invoke()
                    tween(1300, 1800)
                }
            }
        }, label = ""
    ) { state ->
        when (state.value) {
            BoxState.Collapsed -> 0
            BoxState.Expanded -> MID_OFFSET.toInt()
        }
    }
}*/
