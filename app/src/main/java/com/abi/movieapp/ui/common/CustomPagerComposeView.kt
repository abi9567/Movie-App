package com.abi.movieapp.ui.common

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> CustomPagerComposeView (
    pagingItem : LazyPagingItems<T>,
    emptyItemView : @Composable () -> Unit,
    layoutView : @Composable () -> Unit
) {
    val isListEmpty = pagingItem.itemCount == 0

    when(pagingItem.loadState.refresh) {
        is LoadState.Loading -> CustomLoading()
        is LoadState.NotLoading -> {
            if (isListEmpty) emptyItemView()
            else layoutView()
        }
        is LoadState.Error -> {
            //When using local db, remote mediator returns error state in offline case.
            //So fetching data from local db in that case
            if (!isListEmpty) layoutView()
            Log.d("CustomPager", "Load state refresh error")
        }
    }
}