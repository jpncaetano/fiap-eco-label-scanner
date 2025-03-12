package br.com.fiap.ecolabelscanner.model

import com.google.gson.annotations.SerializedName

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
    @SerializedName("image_url") val imageUrl: String?
)
