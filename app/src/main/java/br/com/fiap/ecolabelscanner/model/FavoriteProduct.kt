package br.com.fiap.ecolabelscanner.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Define a tabela de produtos favoritos no banco de dados Room
@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey val code: String, // Código de barras do produto (chave primária)
    val productName: String?, // Nome do produto
    val imageUrl: String? // URL da imagem do produto
)
