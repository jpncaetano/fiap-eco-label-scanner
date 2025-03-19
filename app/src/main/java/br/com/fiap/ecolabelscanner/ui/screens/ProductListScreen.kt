package br.com.fiap.ecolabelscanner.ui.screens

import android.util.Base64
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.viewmodel.FavoriteViewModel
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel
import br.com.fiap.ecolabelscanner.ui.components.BottomNavigationBar
import br.com.fiap.ecolabelscanner.navigation.Screen
import br.com.fiap.ecolabelscanner.model.toFavoriteProduct
import com.google.gson.Gson
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color


@Composable
fun ProductListScreen(
    navController: NavController,
    productViewModel: ProductViewModel = viewModel(),
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    val products by productViewModel.products.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, Screen.ProductList.route) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Resultados da Pesquisa",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                products.isEmpty() -> {
                    Text(
                        text = "Nenhum produto encontrado.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(products) { product ->
                            ProductItem(
                                product = product,
                                favoriteViewModel = favoriteViewModel,
                                onClick = {
                                    try {
                                        val jsonProduct = Gson().toJson(product)
                                        val encodedProduct = Base64.encodeToString(
                                            jsonProduct.toByteArray(Charsets.UTF_8),
                                            Base64.NO_WRAP
                                        )
                                        navController.navigate("productDetail/$encodedProduct")
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, favoriteViewModel: FavoriteViewModel, onClick: () -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isFavorite = favoriteViewModel.isFavorite(product.code ?: "").value
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Adaptação ao tema
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.imageUrl ?: "https://via.placeholder.com/150",
                contentDescription = product.productName ?: "Imagem do produto",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.productName ?: "Nome desconhecido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface // Adaptação ao tema
                )
                Text(text = "Marca: ${product.brands ?: "Desconhecida"}", color = MaterialTheme.colorScheme.onSurface)
                Text(text = "Código de barras: ${product.code}", color = MaterialTheme.colorScheme.onSurface)
                Text(text = "NutriScore: ${product.nutriscoreGrade ?: "N/A"}", color = MaterialTheme.colorScheme.onSurface)
                Text(text = "EcoScore: ${product.ecoscoreGrade ?: "N/A"}", color = MaterialTheme.colorScheme.onSurface)
            }

            IconButton(
                onClick = {
                    if (isFavorite) {
                        favoriteViewModel.removeFavorite(product.toFavoriteProduct())
                    } else {
                        favoriteViewModel.addFavorite(product.toFavoriteProduct())
                    }
                    isFavorite = !isFavorite
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favoritar/Desfavoritar",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

