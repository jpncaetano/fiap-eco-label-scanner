package br.com.fiap.ecolabelscanner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel
import br.com.fiap.ecolabelscanner.ui.theme.*
import androidx.compose.ui.unit.dp




@Composable
fun ProductSearchScreen(viewModel: ProductViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    var products by remember { mutableStateOf<List<Product>?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Digite o nome do produto") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.searchProduct(searchQuery) { result ->
                    products = result
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar")
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            products?.let { productList ->
                items(productList) { product ->
                    ProductItem(product)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Nome: ${product.product_name ?: "Desconhecido"}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Marca: ${product.brands ?: "Desconhecida"}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Código de Barras: ${product.code ?: "Não disponível"}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
