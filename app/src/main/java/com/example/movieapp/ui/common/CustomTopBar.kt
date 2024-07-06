package com.example.movieapp.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MidnightBlue
import com.example.movieapp.ui.theme.SecondaryLightColor

@Composable
fun CustomTopBar(
    text : String,
    description : String? = null,
    onNavigationButtonClick : (() -> Unit)? = null,
    onActionButtonClick : (() -> Unit)? = null,
    @DrawableRes actionButtonResId : Int? = null,
    bottomComposableView : (@Composable () -> Unit)? = null
) {

    val isIconsVisible = !(onNavigationButtonClick == null && onActionButtonClick == null)

    Box(modifier = Modifier
        .background(color = MidnightBlue.copy(alpha = 0.7F))
        .padding(
            vertical = if (isIconsVisible) 12.dp else dimensionResource(id = R.dimen.margin24),
            horizontal = dimensionResource(id = R.dimen.margin8)
        )
        .statusBarsPadding()
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(
                        id = if (isIconsVisible)
                            R.dimen.margin40 else R.dimen.margin16
                    )
                ),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = text,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineMedium)

                description?.let {
                    Text(text = it,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.margin4))
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge.copy(color = SecondaryLightColor))
                }
            }

            bottomComposableView?.let {
                CustomHeightSpacer(dimenResId = R.dimen.margin24)
                it()
            }
        }

        onNavigationButtonClick?.let {
            IconButton(onClick = it,
                modifier = Modifier.align(alignment = Alignment.TopStart)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_go_back),
                    tint = Color.Unspecified,
                    contentDescription = null)
            }
        }

        onActionButtonClick?.let { actionButtonClick ->
            IconButton(onClick = actionButtonClick,
                modifier = Modifier.align(alignment = Alignment.TopEnd)
            ) {
                actionButtonResId?.let {
                    Icon(painter = painterResource(id = it),
                        tint = Color.Unspecified,
                        contentDescription = null)
                }
            }
        }
    }
}