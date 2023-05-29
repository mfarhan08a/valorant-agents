package com.mfarhan08a.valorantagents.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mfarhan08a.valorantagents.R
import com.mfarhan08a.valorantagents.di.Injection
import com.mfarhan08a.valorantagents.model.ListAgent
import com.mfarhan08a.valorantagents.ui.ViewModelFactory
import com.mfarhan08a.valorantagents.ui.common.UiState
import com.mfarhan08a.valorantagents.ui.components.AgentListItem
import com.mfarhan08a.valorantagents.ui.components.SearchBar
import com.mfarhan08a.valorantagents.ui.theme.ValorantAgentsTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by homeViewModel.query
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            query = query,
            onQueryChange = homeViewModel::search,
            modifier = Modifier.background(MaterialTheme.colors.primary)
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
        ) {
            homeViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        homeViewModel.getAllAgents()
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
                                Text(text = stringResource(R.string.no_agent))
                            }
                    }
                    is UiState.Error -> {}
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    agentList: List<ListAgent>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier.testTag("AgentList")
    ) {
        items(agentList, key = { it.agent.id }) { data ->
            AgentListItem(
                id = data.agent.id,
                name = data.agent.name,
                role = data.agent.role,
                icon = data.agent.icon,
                navigateToDetail = navigateToDetail,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(tween(durationMillis = 500))
            )
            Divider()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ValorantAgentsTheme {
        HomeScreen(navigateToDetail = {})
    }
}