package com.example.movieapp.ui.common

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

    when(pagingItem.loadState.refresh) {
        is LoadState.Loading -> CustomLoading()
        is LoadState.NotLoading -> {
            if (pagingItem.itemCount == 0) emptyItemView()
            else layoutView()
        }
        is LoadState.Error -> {
            Log.d("CustomPager", "Load state refresh error")
        }
    }
}