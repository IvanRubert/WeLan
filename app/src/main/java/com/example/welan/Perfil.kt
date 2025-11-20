package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.welan.data.User

@Composable
fun PerfilUser(user: User, navController: NavController, modifier: Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()) {
        Text(
            text = "Tu Perfil",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row {
            Box(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(user.imageRes),
                    contentDescription = user.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape).size(100.dp)
                        .align(Alignment.Center).padding(top = 16.dp, end = 16.dp)
                )
            }
            Column(modifier = Modifier.padding(top = 8.dp, end = 16.dp, bottom = 16.dp)) {
                Text(
                    text = "Nombre completo",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = user.name, modifier = Modifier.padding(bottom = 16.dp))

                Text(
                    text = "Email",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = user.email, modifier = Modifier.padding(bottom = 16.dp))

                Text(
                    text = "Ocupación",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = user.occupation, modifier = Modifier.padding(bottom = 16.dp))
            }
        }
        Text(
            text = "Intereses",
            style = MaterialTheme.typography.titleMedium
        )
        Card(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                items(user.intereses){interes ->
                    Card(colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = interes, textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(8.dp))
                    }
                }
            }
        }
    }
}