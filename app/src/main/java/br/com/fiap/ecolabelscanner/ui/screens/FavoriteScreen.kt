package br.com.fiap.ecolabelscanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.ecolabelscanner.model.FavoriteProduct
import br.com.fiap.ecolabelscanner.viewmodel.FavoriteViewModel
import br.com.fiap.ecolabelscanner.navigation.Screen
import br.com.fiap.ecolabelscanner.ui.components.BottomNavigationBar

@Composable
fun FavoritesScreen(navController: NavController, viewModel: FavoriteViewModel = viewModel()) {
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, Screen.Favorites.route) } // Barra de navegação fixa na parte inferior
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Meus Favoritos",
                style = MaterialTheme.typography.headlineSmall
            )

            // Exibe uma mensagem caso a lista de favoritos esteja vazia
            if (favorites.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Nenhum favorito adicionado ainda.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                // Lista de produtos favoritos
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(favorites) { product ->
                        FavoriteItem(product, onDeleteClick = { viewModel.removeFavorite(product) })
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(product: FavoriteProduct, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.productName ?: "Nome não disponível",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(text = "Código: ${product.code}", style = MaterialTheme.typography.bodySmall)
            }
            // Botão para remover um produto dos favoritos
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remover")
            }
        }
    }
}
