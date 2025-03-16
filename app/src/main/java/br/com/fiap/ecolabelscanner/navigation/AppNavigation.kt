package br.com.fiap.ecolabelscanner.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.ecolabelscanner.ui.ProductSearchScreen
import br.com.fiap.ecolabelscanner.ui.ProductDetailScreen
import br.com.fiap.ecolabelscanner.model.Product
import com.google.gson.Gson

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(Screen.Search.route) {
            ProductSearchScreen(navController = navController)
        }

        composable(Screen.Detail.route) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson") ?: ""
            val product = Gson().fromJson(Uri.decode(productJson), Product::class.java)
            ProductDetailScreen(product = product, navController = navController)
        }
    }
}
