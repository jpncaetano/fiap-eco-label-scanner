package br.com.fiap.ecolabelscanner.navigation

// Define as rotas de navegação do aplicativo
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Favorites : Screen("favorites")
    object ProductList : Screen("product_list")
}
