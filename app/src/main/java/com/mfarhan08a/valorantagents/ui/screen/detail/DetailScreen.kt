package com.mfarhan08a.valorantagents.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mfarhan08a.valorantagents.R
import com.mfarhan08a.valorantagents.di.Injection
import com.mfarhan08a.valorantagents.model.Role
import com.mfarhan08a.valorantagents.ui.ViewModelFactory
import com.mfarhan08a.valorantagents.ui.common.UiState
import com.mfarhan08a.valorantagents.ui.components.FavoriteButton
import com.mfarhan08a.valorantagents.ui.theme.ValorantAgentsTheme

@Composable
fun DetailScreen(
    agentId: Long,
    detailViewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {

    detailViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                detailViewModel.getAgentById(agentId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.agent.name,
                    data.agent.role,
                    data.agent.biography,
                    data.agent.art,
                    data.isFavorite,
                    onBackClick = navigateBack,
                    onClick = { favorite ->
                        detailViewModel.addToFavorite(data.agent, favorite)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}


@Composable
fun DetailContent(
    name: String,
    role: Role,
    biography: String,
    @DrawableRes art: Int,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onClick: (favorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var favorite by rememberSaveable { mutableStateOf(isFavorite) }
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(art),
                    contentDescription = name,
                    contentScale = ContentScale.FillHeight,
                    modifier = modifier
                        .padding(top = 36.dp)
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(role.roleIcon),
                        contentDescription = role.roleName,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = role.roleName,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                }
                Text(
                    text = biography,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )

        FavoriteButton(
            isFavorite = favorite,
            onClick = {
                favorite = !favorite
                onClick(favorite)
            },
            modifier = Modifier.padding(16.dp)
        )

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    ValorantAgentsTheme {
        DetailContent(
            "Brimstone",
            Role.CONTROLLER,
            "hehe boi",
            R.drawable.brimstone,
            true,
            {},
            {}
        )
    }
}