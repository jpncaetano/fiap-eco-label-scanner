package br.com.fiap.ecolabelscanner.model

import com.google.gson.annotations.SerializedName

// Representa a resposta da API OpenFoodFacts para buscas de produtos
data class SearchResponse(
    @SerializedName("products") val products: List<Product> = emptyList(),
    @SerializedName("product") val product: Product? = null
)
