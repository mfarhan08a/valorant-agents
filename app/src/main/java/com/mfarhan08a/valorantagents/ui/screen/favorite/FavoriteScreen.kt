package com.mfarhan08a.valorantagents.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mfarhan08a.valorantagents.R
import com.mfarhan08a.valorantagents.di.Injection
import com.mfarhan08a.valorantagents.ui.ViewModelFactory
import com.mfarhan08a.valorantagents.ui.common.UiState
import com.mfarhan08a.valorantagents.ui.screen.home.HomeContent

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    favoriteViewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
    ) {
        favoriteViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    favoriteViewModel.getFavoriteAgents()
                }
                is UiState.Success -> {
                    HomeContent(
                        agentList = uiState.data,
                        modifier = Modifier,
                        navigateToDetail = navigateToDetail,
                    )
                    if (uiState.data.isEmpty())
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Text(text = stringResource(R.string.empty_favorite))
                    }
                }
                is UiState.Error -> {}
            }
        }
    }
}