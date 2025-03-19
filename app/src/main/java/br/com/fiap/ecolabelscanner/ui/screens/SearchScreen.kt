package br.com.fiap.ecolabelscanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel
import br.com.fiap.ecolabelscanner.navigation.Screen
import br.com.fiap.ecolabelscanner.ui.components.BottomNavigationBar
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import br.com.fiap.ecolabelscanner.ui.theme.GreenPrimary

@Composable
fun SearchScreen(navController: NavController, viewModel: ProductViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    var searchType by remember { mutableStateOf("name") }
    var showWarning by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, Screen.Search.route) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Buscar Produto",
                style = MaterialTheme.typography.headlineSmall,
                color = GreenPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    showWarning = false // ✅ Esconde o aviso ao digitar
                },
                label = { Text("Digite o nome ou código de barras") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = searchType == "name",
                    onClick = { searchType = "name" }
                )
                Text(text = "Nome", modifier = Modifier.padding(start = 8.dp))

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = searchType == "barcode",
                    onClick = { searchType = "barcode" }
                )
                Text(text = "Código de Barras", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (query.isBlank()) {
                        showWarning = true
                    } else {
                        if (searchType == "name") {
                            viewModel.searchProductsByName(query)
                        } else {
                            viewModel.fetchProductByBarcode(query)
                        }
                        navController.navigate(Screen.ProductList.route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text(text = "Pesquisar", color = Color.White)
            }

            // ** Exibir aviso se o campo estiver vazio **
            if (showWarning) {
                Text(
                    text = "⚠️ Digite o nome de um produto ou código de barras válido!",
                    color = Color.Red,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
