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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.viewmodel.*

import androidx.compose.ui.draw.clip

@Composable
fun ProductSearchScreen(viewModel: ProductViewModel = ProductViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var hasSearched by remember { mutableStateOf(false) } // Estado para rastrear se já houve busca

    // Se um produto for selecionado, exibe a tela de detalhes
    if (selectedProduct != null) {
        ProductDetailScreen(product = selectedProduct!!) {
            selectedProduct = null
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Digite o nome do produto ou o código de barras") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (searchQuery.isNotBlank()) { // Evita busca vazia
                        hasSearched = true
                        isLoading = true

                        // Verifica se a entrada contém apenas números (é um código de barras)
                        if (searchQuery.all { it.isDigit() }) {
                            viewModel.searchProduct(searchQuery) { result ->
                                products = result
                                isLoading = false
                            }
                        } else {
                            viewModel.searchProduct(searchQuery) { result ->
                                products = result
                                isLoading = false
                            }
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

            // Se a busca já foi feita e não houver resultados, exibir mensagem de erro
            if (hasSearched && products.isEmpty() && !isLoading) {
                Text(
                    text = "Nenhum produto encontrado. Tente novamente!",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Exibir lista de produtos encontrados
            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
                items(products) { product ->
                    ProductItem(product) { selectedProduct = it }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: (Product) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)), // Fundo lilás claro
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
        }
    }
}
