package com.mfarhan08a.valorantagents.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mfarhan08a.valorantagents.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 36.dp)
    ) {
        Card(
            shape = CircleShape,
            elevation = 2.dp,
            modifier = Modifier
                .height(280.dp)
                .width(280.dp)

        ) {
            Image(
                painterResource(R.drawable.farhan),
                contentDescription = stringResource(id = R.string.menu_profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = stringResource(R.string.name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .padding(top = 28.dp)
        )
        Text(
            text = stringResource(R.string.email),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .padding(top = 12.dp)
        )
    }
}