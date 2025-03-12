package br.com.fiap.ecolabelscanner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.draw.clip


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(product: Product, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalhes do Produto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Nome do Produto
            Text(
                text = product.productName ?: "Nome desconhecido",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Imagem do Produto
            if (!product.imageUrl.isNullOrEmpty()) {
                Image(
                    painter = rememberImagePainter(product.imageUrl),
                    contentDescription = "Imagem do Produto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Informações do Produto**
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Informações do Produto:",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Marca: ${product.brands ?: "Desconhecida"}", fontSize = 14.sp)
                    Text(
                        text = "Código de Barras: ${product.code ?: "Não disponível"}",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Nutri-Score: ${product.nutriscoreGrade ?: "Não disponível"}",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Eco-Score: ${product.ecoscoreGrade ?: "Não disponível"}",
                        fontSize = 14.sp
                    )
                }
            }

            // Informações Nutricionais
            if (product.nutriments != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Informações Nutricionais:",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Energia: ${product.nutriments.energyKcal ?: "Não disponível"} kcal",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Gorduras Totais: ${product.nutriments.fat ?: "Não disponível"} g",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Gorduras Saturadas: ${product.nutriments.saturatedFat ?: "Não disponível"} g",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Carboidratos: ${product.nutriments.carbohydrates ?: "Não disponível"} g",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Açúcares: ${product.nutriments.sugars ?: "Não disponível"} g",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Proteínas: ${product.nutriments.proteins ?: "Não disponível"} g",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Sal: ${product.nutriments.salt ?: "Não disponível"} g",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Fibras: ${product.nutriments.fiber ?: "Não disponível"} g",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

