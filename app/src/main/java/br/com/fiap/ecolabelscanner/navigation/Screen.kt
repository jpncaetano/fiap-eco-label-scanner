package br.com.fiap.ecolabelscanner.navigation

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object Detail : Screen("detail/{productJson}") {
        fun createRoute(productJson: String) = "detail/$productJson"
    }
}