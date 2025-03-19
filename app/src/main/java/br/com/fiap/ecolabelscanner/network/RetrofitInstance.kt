package br.com.fiap.ecolabelscanner.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://world.openfoodfacts.org/"

    // Interceptor para logar requisições e respostas HTTP no Logcat
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cliente HTTP configurado com o interceptor de logging
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Instância singleton do Retrofit configurada para consumir a API Open Food Facts
    val api: OpenFoodFactsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON para objetos Kotlin
            .build()
            .create(OpenFoodFactsApiService::class.java)
    }
}
