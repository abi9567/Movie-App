package com.abi.movieapp.ui.screens.homeScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.abi.movieapp.R
import com.abi.movieapp.internal.enums.Language
import com.abi.movieapp.ui.common.CustomGradientButton
import com.abi.movieapp.ui.common.CustomModalBottomSheetLayout
import com.abi.movieapp.ui.common.CustomScaffold
import com.abi.movieapp.ui.common.CustomWidthSpacer
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val movieList = viewModel.filmList.collectAsLazyPagingItems()
    val state = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val languages = viewModel.languageList
    val currentLanguage = viewModel.currentLanguage.observeAsState().value ?: Language.Malayalam.name

    CustomModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            LanguageSelectionBottomSheet(languages = languages,
                onClick = { selectedLanguage ->
                    viewModel.changeLanguage(language = selectedLanguage)
                    scope.launch { modalBottomSheetState.hide() }
            })
        }
    ) {
        CustomScaffold(topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.margin16))
                    .padding(bottom = dimensionResource(id = R.dimen.margin4))
                    .statusBarsPadding()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                with(sharedTransitionScope) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo),
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .offset(y = 3.dp, x = (-13).dp)
                            .size(size = 54.dp)
                            .sharedElement(
                                state = rememberSharedContentState(key = "icon"),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        contentDescription = null
                    )
                }

                CustomWidthSpacer(dimenResId = R.dimen.margin16)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )

                    CustomWidthSpacer(dimenResId = R.dimen.margin8)

                    Text(
                        text = "Calicut",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                CustomWidthSpacer(dimenResId = R.dimen.margin16)

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        scope.launch { modalBottomSheetState.show() }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_language),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )

                    CustomWidthSpacer(dimenResId = R.dimen.margin8)

                    Text(
                        text = currentLanguage,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                CustomWidthSpacer(dimenResId = R.dimen.margin16)

                CustomGradientButton(text = stringResource(id = R.string.login),
                    textStyle = MaterialTheme.typography.titleSmall,
                    onClick = { })

            }
        }) { topBarPadding ->
            HomeScreenFilmPaginationView(
                state = state,
                modifier = Modifier.padding(paddingValues = topBarPadding),
                movieList = movieList,
                isSearchView = false,
                navController = navController
            )
        }
    }
}