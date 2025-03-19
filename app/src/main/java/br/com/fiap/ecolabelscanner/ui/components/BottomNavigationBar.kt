package br.com.fiap.ecolabelscanner.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.fiap.ecolabelscanner.navigation.Screen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String) {
    // Barra de navegação inferior com duas opções: Pesquisa e Favoritos
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Botão para a tela de pesquisa
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Pesquisar") },
            label = { Text("Pesquisar") },
            selected = currentRoute == Screen.Search.route,
            onClick = { navController.navigate(Screen.Search.route) }
        )

        // Botão para a tela de favoritos
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = currentRoute == Screen.Favorites.route,
            onClick = { navController.navigate(Screen.Favorites.route) }
        )
    }
}
