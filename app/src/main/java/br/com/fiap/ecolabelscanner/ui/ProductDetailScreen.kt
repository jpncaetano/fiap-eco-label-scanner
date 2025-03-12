package br.com.fiap.ecolabelscanner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import br.com.fiap.ecolabelscanner.model.Product
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(product: Product, onBack: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalhes do Produto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
            // ** Nome do Produto **
            Text(
                text = product.productName ?: "Nome desconhecido",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

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
            Text(text = "Marca: ${product.brands ?: "Desconhecida"}", fontSize = 14.sp)
            Text(text = "Código de Barras: ${product.code ?: "Não disponível"}", fontSize = 14.sp)

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
                            fontSize = 16.sp,
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
                    Text(text = "🌱 Eco-Score: ${product.ecoscoreGrade ?: "Não disponível"}", fontSize = 14.sp)
                    Text(text = "🍏 Nutri-Score: ${product.nutriscoreGrade ?: "Não disponível"}", fontSize = 14.sp)
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
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100) // Laranja escuro
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Energia: ${product.nutriments?.energyKcal ?: "Não disponível"} kcal", fontSize = 14.sp)
                    Text(text = "Gorduras Totais: ${product.nutriments?.fat ?: "Não disponível"} g", fontSize = 14.sp)
                    Text(text = "Gorduras Saturadas: ${product.nutriments?.saturatedFat ?: "Não disponível"} g", fontSize = 14.sp)
                    Text(text = "Carboidratos: ${product.nutriments?.carbohydrates ?: "Não disponível"} g", fontSize = 14.sp)
                    Text(text = "Açúcares: ${product.nutriments?.sugars ?: "Não disponível"} g", fontSize = 14.sp)
                    Text(text = "Proteínas: ${product.nutriments?.proteins ?: "Não disponível"} g", fontSize = 14.sp)
                    Text(text = "Sal: ${product.nutriments?.salt ?: "Não disponível"} g", fontSize = 14.sp)
                    Text(text = "Fibras: ${product.nutriments?.fiber ?: "Não disponível"} g", fontSize = 14.sp)
                }
            }

            // ** Popup Explicativo sobre ESG **
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "O que são Nutri-Score e Eco-Score?", fontWeight = FontWeight.Bold) },
                    text = {
                        Column {
                            // Eco-Score
                            Text(
                                text = "🌿 Eco-Score (Impacto Ambiental)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF388E3C) // Verde
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("O Eco-Score avalia o impacto ambiental de um alimento ao longo do seu ciclo de vida. "
                                    + "A classificação vai de A (verde - melhor impacto) a E (vermelho - pior impacto).")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("🔎 Critérios:", fontWeight = FontWeight.Bold)
                            Text("• Produção: uso de recursos naturais e emissões de carbono.")
                            Text("• Transporte: distância e meio de transporte.")
                            Text("• Cultivo: se é orgânico ou industrializado.")
                            Text("• Embalagem: impacto ambiental dos materiais.")

                            Spacer(modifier = Modifier.height(16.dp))

                            // Nutri-Score
                            Text(
                                text = "🍎 Nutri-Score (Qualidade Nutricional)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100) // Laranja
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("O Nutri-Score ajuda consumidores a escolherem produtos mais saudáveis. "
                                    + "A escala vai de A (verde - saudável) a E (vermelho - menos saudável).")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("🔎 Como é calculado?", fontWeight = FontWeight.Bold)
                            Text("• Pontos negativos: gorduras saturadas, açúcares, calorias e sódio.")
                            Text("• Pontos positivos: proteínas, fibras, frutas e vegetais.")
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
