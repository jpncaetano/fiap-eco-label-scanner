package br.com.fiap.ecolabelscanner.model

import com.google.gson.annotations.SerializedName

data class ProductSearchResponse(
    @SerializedName("products") val products: List<Product>
)
