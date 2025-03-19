package br.com.fiap.ecolabelscanner.navigation

import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.ecolabelscanner.ui.screens.*
import br.com.fiap.ecolabelscanner.viewmodel.FavoriteViewModel
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel
import com.google.gson.Gson
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel()
    val favoriteViewModel: FavoriteViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController, productViewModel)
        }
        composable(Screen.ProductList.route) {
            ProductListScreen(navController, productViewModel, favoriteViewModel)
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController, favoriteViewModel)
        }

        // Rota para a Tela de Detalhes do Produto
        composable(
            route = "productDetail/{product}",
            arguments = listOf(navArgument("product") { type = NavType.StringType })
        ) { backStackEntry ->
            val jsonProduct = backStackEntry.arguments?.getString("product")
            val decodedBytes = Base64.decode(jsonProduct, Base64.NO_WRAP)
            val decodedProduct = String(decodedBytes, StandardCharsets.UTF_8)
            val product = Gson().fromJson(decodedProduct, br.com.fiap.ecolabelscanner.model.Product::class.java)

            ProductDetailScreen(
                product = product,
                favoriteViewModel = favoriteViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
