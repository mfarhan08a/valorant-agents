package com.mfarhan08a.valorantagents.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mfarhan08a.valorantagents.model.Role
import com.mfarhan08a.valorantagents.ui.theme.ValorantAgentsTheme

@Composable
fun AgentListItem(
    id: Long,
    name: String,
    role: Role,
    icon: String,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { navigateToDetail(id) }
    ) {
        AsyncImage(
            model = icon,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp)
                .clip(CircleShape)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,

                )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(role.roleIcon),
                    contentDescription = role.roleName,
                    modifier = Modifier
                        .size(20.dp)

                )
                Text(
                    text = role.roleName,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp)

                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListItemPreview() {
    ValorantAgentsTheme {
        AgentListItem(
            id = 1,
            name = "Brimstone",
            role = Role.CONTROLLER,
            icon = "https://static.wikia.nocookie.net/valorant/images/4/4d/Brimstone_icon.png",
            navigateToDetail = {}
        )
    }
}