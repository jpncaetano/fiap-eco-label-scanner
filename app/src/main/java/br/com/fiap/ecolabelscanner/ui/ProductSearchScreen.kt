package br.com.fiap.ecolabelscanner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel

@Composable
fun ProductSearchScreen(viewModel: ProductViewModel = ProductViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Digite o nome do produto") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.searchProduct(searchQuery) { result ->  // 🚀 Corrigido aqui!
                    products = result
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Buscar")
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nome: ${product.productName ?: "Desconhecido"}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Marca: ${product.brands ?: "Desconhecida"}")
            Text(text = "Código de Barras: ${product.code ?: "Não disponível"}")
            Text(text = "Nutri-Score: ${product.nutriscoreGrade ?: "Não disponível"}")
            Text(text = "Eco-Score: ${product.ecoscoreGrade ?: "Não disponível"}")
            Text(text = "Ingredientes: ${product.ingredients ?: "Não informado"}")
            Text(text = "Aditivos: ${product.additives?.joinToString() ?: "Nenhum"}")
            Text(text = "Alérgenos: ${product.allergens ?: "Nenhum"}")
            Text(text = "Origem: ${product.origins ?: "Não informado"}")

            Spacer(modifier = Modifier.height(8.dp))

            if (!product.imageUrl.isNullOrEmpty()) {
                Image(
                    painter = rememberImagePainter(product.imageUrl),
                    contentDescription = "Imagem do Produto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
