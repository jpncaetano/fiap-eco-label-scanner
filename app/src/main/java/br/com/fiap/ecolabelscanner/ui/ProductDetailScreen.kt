package br.com.fiap.ecolabelscanner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import br.com.fiap.ecolabelscanner.model.Product
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.Alignment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(product: Product, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) } // Estado para abrir o popup

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalhes do Produto", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
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
            // ** Nome do Produto - Formatação restaurada **
            Text(
                text = "Nome: ${product.productName ?: "Nome desconhecido"}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ** Imagem do Produto **
            if (!product.imageUrl.isNullOrEmpty()) {
                Image(
                    painter = rememberImagePainter(product.imageUrl),
                    contentDescription = "Imagem do Produto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ** Informações Básicas **
            Text(text = "Marca: ${product.brands ?: "Desconhecida"}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = "Código de Barras: ${product.code ?: "Não disponível"}", fontSize = 16.sp, fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(16.dp))

            // ** Card de Informações ESG **
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)) // Verde claro para ESG
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "📊 Informações ESG",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF388E3C) // Verde escuro
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Informações ESG",
                                tint = Color(0xFF388E3C)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "🌱 Eco-Score: ${product.ecoscoreGrade ?: "Não disponível"}", fontSize = 16.sp)
                    Text(text = "🍏 Nutri-Score: ${product.nutriscoreGrade ?: "Não disponível"}", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ** Card de Informações Nutricionais **
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)) // Laranja claro para Nutrição
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🥗 Informações Nutricionais",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100) // Laranja escuro
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Energia: ${product.nutriments?.energyKcal ?: "Não disponível"} kcal", fontSize = 16.sp)
                    Text(text = "Gorduras Totais: ${product.nutriments?.fat ?: "Não disponível"} g", fontSize = 16.sp)
                    Text(text = "Carboidratos: ${product.nutriments?.carbohydrates ?: "Não disponível"} g", fontSize = 16.sp)
                    Text(text = "Açúcares: ${product.nutriments?.sugars ?: "Não disponível"} g", fontSize = 16.sp)
                    Text(text = "Proteínas: ${product.nutriments?.proteins ?: "Não disponível"} g", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ** Card de Ingredientes **
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD1C4E9)) // Roxo claro
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📝 Ingredientes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF512DA8) // Roxo escuro
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = product.ingredients ?: "Não disponível", fontSize = 16.sp)
                }
            }

            // ** Popup Explicativo sobre ESG **
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "O que são Nutri-Score e Eco-Score?", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                    text = {
                        Column {
                            Text(
                                text = "🌿 Eco-Score (Impacto Ambiental)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF388E3C) // Verde
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("O Eco-Score avalia o impacto ambiental de um alimento ao longo do seu ciclo de vida. "
                                    + "A classificação vai de A (verde - melhor impacto) a E (vermelho - pior impacto).")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("🍎 Nutri-Score (Qualidade Nutricional)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                            Text("O Nutri-Score classifica alimentos com base na qualidade nutricional, variando de A (mais saudável) a E (menos saudável).")
                        }
                    },
                    confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Entendi")
                        }
                    }
                )
            }
        }
    }
}
