package com.abi.movieapp.ui.screens.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.abi.movieapp.R
import com.abi.movieapp.ui.common.CustomHeightSpacer
import com.abi.movieapp.ui.common.CustomScaffold
import com.abi.movieapp.ui.common.CustomTextBoxView
import com.abi.movieapp.ui.common.CustomTopBar
import com.abi.movieapp.ui.screens.homeScreen.HomeScreenFilmPaginationView

@Composable
fun SearchScreen(
    navController : NavController,
    viewModel : SearchViewModel
) {
    val searchMoviesList = viewModel.searchMovies.collectAsLazyPagingItems()
    val searchKey by viewModel.searchKey.collectAsState(initial = null)
    val state = rememberLazyGridState()
    val focusManager = LocalFocusManager.current

    if (state.isScrollInProgress) {
        focusManager.clearFocus()
    }

    CustomScaffold(topBar = {
        CustomTopBar(text = stringResource(id = R.string.search_movie),
            onActionButtonClick = { navController.navigateUp() },
            actionButtonResId = R.drawable.ic_close)
    }) { paddingValues ->

        Column(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxWidth()) {

            CustomHeightSpacer(dimenResId = R.dimen.margin16)

            CustomTextBoxView(value = searchKey,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin16)),
                placeHolderText = stringResource(id = R.string.search_movie),
                onValueChane = viewModel::setSearchKey)

            HomeScreenFilmPaginationView(
                state = state,
                movieList = searchMoviesList,
                isSearchView = true,
                navController = navController
            )
        }
    }
}