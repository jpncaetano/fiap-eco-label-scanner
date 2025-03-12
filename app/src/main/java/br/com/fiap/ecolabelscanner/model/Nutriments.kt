package br.com.fiap.ecolabelscanner.model

import com.google.gson.annotations.SerializedName

data class Nutriments(
    @SerializedName("energy-kcal_100g") val energyKcal: Double?,
    @SerializedName("fat_100g") val fat: Double?,
    @SerializedName("saturated-fat_100g") val saturatedFat: Double?,
    @SerializedName("carbohydrates_100g") val carbohydrates: Double?,
    @SerializedName("sugars_100g") val sugars: Double?,
    @SerializedName("proteins_100g") val proteins: Double?,
    @SerializedName("salt_100g") val salt: Double?,
    @SerializedName("fiber_100g") val fiber: Double?
)
