package br.com.fiap.ecolabelscanner.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.ecolabelscanner.viewmodel.FavoriteViewModel
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel
import br.com.fiap.ecolabelscanner.model.Product

@Composable
fun NavGraph(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    favoriteViewModel: FavoriteViewModel
) {
    NavHost(navController = navController, startDestination = "productSearch") {

        composable("productSearch") {
            ProductSearchScreen(
                productViewModel = productViewModel,
                favoriteViewModel = favoriteViewModel,
                navigateToDetail = { product ->
                    navController.navigate(
                        "productDetail/${product.code}" +
                                "?name=${product.productName}" +
                                "&brand=${product.brands}" +
                                "&nutriscore=${product.nutriscoreGrade}" +
                                "&ecoscore=${product.ecoscoreGrade}" +
                                "&ingredients=${product.ingredients}" +
                                "&additives=${product.additives?.joinToString(",")}" +
                                "&allergens=${product.allergens}" +
                                "&origins=${product.origins}" +
                                "&imageUrl=${product.imageUrl}"
                    )
                },
                navigateToFavorites = {
                    navController.navigate("favorites")
                }
            )
        }

        composable(
            route = "productDetail/{barcode}" +
                    "?name={name}" +
                    "&brand={brand}" +
                    "&nutriscore={nutriscore}" +
                    "&ecoscore={ecoscore}" +
                    "&ingredients={ingredients}" +
                    "&additives={additives}" +
                    "&allergens={allergens}" +
                    "&origins={origins}" +
                    "&imageUrl={imageUrl}"
        ) { backStackEntry ->
            val product = Product(
                code = backStackEntry.arguments?.getString("barcode") ?: "",
                productName = backStackEntry.arguments?.getString("name"),
                brands = backStackEntry.arguments?.getString("brand"),
                nutriscoreGrade = backStackEntry.arguments?.getString("nutriscore"),
                ecoscoreGrade = backStackEntry.arguments?.getString("ecoscore"),
                ingredients = backStackEntry.arguments?.getString("ingredients"),
                additives = backStackEntry.arguments?.getString("additives")?.split(","),
                allergens = backStackEntry.arguments?.getString("allergens"),
                origins = backStackEntry.arguments?.getString("origins"),
                imageUrl = backStackEntry.arguments?.getString("imageUrl"),
                nutriments = null // ⚠️ Ajuste isso se precisar carregar os `nutriments`
            )

            ProductDetailScreen(
                product = product,
                favoriteViewModel = favoriteViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("favorites") {
            FavoriteScreen(
                favoriteViewModel = favoriteViewModel,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
