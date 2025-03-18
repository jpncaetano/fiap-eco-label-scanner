package br.com.fiap.ecolabelscanner.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.viewmodel.FavoriteViewModel
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel

@Composable
fun ProductSearchScreen(
    productViewModel: ProductViewModel,
    favoriteViewModel: FavoriteViewModel,
    navigateToDetail: (Product) -> Unit,
    navigateToFavorites: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var hasSearched by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Botão para acessar os favoritos
        Button(
            onClick = { navigateToFavorites() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Favoritos")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de busca
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Digite o nome do produto ou o código de barras") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botão de busca
        Button(
            onClick = {
                if (searchQuery.isNotBlank()) {
                    hasSearched = true
                    isLoading = true

                    productViewModel.searchProduct(searchQuery) { result ->
                        products = result
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
            } else {
                Text("Buscar")
            }
        }

        // Mensagem de erro caso não encontre produtos
        if (hasSearched && products.isEmpty() && !isLoading) {
            Text(
                text = "Nenhum produto encontrado. Tente novamente!",
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Lista de produtos encontrados
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    favoriteViewModel = favoriteViewModel,
                    onClick = { navigateToDetail(product) }
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    favoriteViewModel: FavoriteViewModel,
    onClick: (Product) -> Unit
) {
    val isFavorite by favoriteViewModel.isFavorite(product.code ?: "").collectAsState(initial = false)

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(product) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nome: ${product.productName ?: "Desconhecido"}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = "Marca: ${product.brands ?: "Desconhecida"}", fontSize = 14.sp)
            Text(text = "Código de Barras: ${product.code ?: "Não disponível"}", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(8.dp))

            if (!product.imageUrl.isNullOrEmpty()) {
                Image(
                    painter = rememberImagePainter(product.imageUrl),
                    contentDescription = "Imagem do Produto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isFavorite) "Remover dos Favoritos" else "Adicionar aos Favoritos",
                    fontSize = 14.sp,
                    color = if (isFavorite) Color.Red else Color.Gray
                )

                IconButton(
                    onClick = {
                        if (isFavorite) {
                            favoriteViewModel.removeFavorite(product.toFavoriteProduct())
                        } else {
                            favoriteViewModel.addFavorite(product.toFavoriteProduct())
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remover dos favoritos" else "Adicionar aos favoritos",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}
