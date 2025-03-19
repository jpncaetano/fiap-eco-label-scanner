package br.com.fiap.ecolabelscanner.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// Representa um produto retornado pela API OpenFoodFacts
data class Product(
    @SerializedName("product_name") val productName: String?,
    @SerializedName("brands") val brands: String?,
    @SerializedName("code") val code: String?,
    @SerializedName("nutriscore_grade") val nutriscoreGrade: String?,
    @SerializedName("ecoscore_grade") val ecoscoreGrade: String?,
    @SerializedName("ingredients_text") val ingredients: String?,
    @SerializedName("additives_tags") val additives: List<String>?,
    @SerializedName("allergens") val allergens: String?,
    @SerializedName("origins") val origins: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("nutriments") val nutriments: Nutriments?
)

// Converte um Product para um FavoriteProduct para armazenamento local
fun Product.toFavoriteProduct(): FavoriteProduct {
    return FavoriteProduct(
        code = this.code ?: "",
        productName = this.productName ?: "Nome desconhecido",
        imageUrl = this.imageUrl ?: ""
    )
}
