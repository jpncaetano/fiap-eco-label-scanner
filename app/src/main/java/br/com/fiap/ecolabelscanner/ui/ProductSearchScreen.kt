package br.com.fiap.ecolabelscanner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.navigation.NavController
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel
import br.com.fiap.ecolabelscanner.navigation.Screen
import com.google.gson.Gson
import android.net.Uri

@Composable
fun ProductSearchScreen(
    viewModel: ProductViewModel = ProductViewModel(),
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var hasSearched by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Digite o nome do produto ou o código de barras") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (searchQuery.isNotBlank()) {
                    hasSearched = true
                    isLoading = true
                    viewModel.searchProduct(searchQuery) { result ->
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

        if (hasSearched && products.isEmpty() && !isLoading) {
            Text(
                text = "Nenhum produto encontrado. Tente novamente!",
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(products) { product ->
                ProductItem(product) {
                    val productJson = Uri.encode(Gson().toJson(product)) // Codifica o JSON corretamente
                    navController.navigate(Screen.Detail.createRoute(productJson))
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
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
        }
    }
}
