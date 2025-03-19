package br.com.fiap.ecolabelscanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.viewmodel.FavoriteViewModel
import br.com.fiap.ecolabelscanner.model.toFavoriteProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    favoriteViewModel: FavoriteViewModel,
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val isFavorite by favoriteViewModel.isFavorite(product.code ?: "").collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalhes do Produto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
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
                fontSize = 22.sp,
                style = MaterialTheme.typography.headlineSmall
            )

            // Botão de Favorito
            FavoriteButton(product = product, viewModel = favoriteViewModel)

            Spacer(modifier = Modifier.height(8.dp))

            // Imagem do Produto
            if (!product.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = "Imagem do Produto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Card de Informações ESG
            ProductInfoCard(
                title = "📊 Informações ESG",
                content = listOf(
                    "🌱 Eco-Score: ${product.ecoscoreGrade ?: "Não disponível"}",
                    "🍏 Nutri-Score: ${product.nutriscoreGrade ?: "Não disponível"}"
                ),
                onInfoClick = { showDialog = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card de Informações Nutricionais
            ProductInfoCard(
                title = "🥗 Informações Nutricionais",
                content = listOf(
                    "Energia: ${product.nutriments?.energyKcal ?: "Não disponível"} kcal",
                    "Gorduras Totais: ${product.nutriments?.fat ?: "Não disponível"} g",
                    "Carboidratos: ${product.nutriments?.carbohydrates ?: "Não disponível"} g",
                    "Proteínas: ${product.nutriments?.proteins ?: "Não disponível"} g"
                )
            )

            // Popup Explicativo sobre ESG
            if (showDialog) {
                ESGInfoDialog(onDismiss = { showDialog = false })
            }
        }
    }
}

@Composable
fun FavoriteButton(product: Product, viewModel: FavoriteViewModel) {
    val isFavorite by viewModel.isFavorite(product.code ?: "").collectAsState(initial = false)

    IconButton(
        onClick = {
            if (isFavorite) {
                viewModel.removeFavorite(product.toFavoriteProduct())
            } else {
                viewModel.addFavorite(product.toFavoriteProduct())
            }
        }
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favoritar/Desfavoritar",
            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ProductInfoCard(title: String, content: List<String>, onInfoClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, fontSize = 18.sp, style = MaterialTheme.typography.bodyLarge)
                if (onInfoClick != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(onClick = onInfoClick) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Informações",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            content.forEach {
                Text(text = it, fontSize = 14.sp, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun ESGInfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "O que são Nutri-Score e Eco-Score?", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text(
                    text = "🌿 Eco-Score (Impacto Ambiental)",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "O Eco-Score avalia o impacto ambiental de um alimento ao longo do seu ciclo de vida. "
                            + "A classificação vai de A (verde - melhor impacto) a E (vermelho - pior impacto)."
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "🍎 Nutri-Score (Qualidade Nutricional)",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "O Nutri-Score ajuda consumidores a escolherem produtos mais saudáveis. "
                            + "A escala vai de A (verde - saudável) a E (vermelho - menos saudável)."
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Entendi")
            }
        }
    )
}
