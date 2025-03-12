package br.com.fiap.ecolabelscanner.network

import br.com.fiap.ecolabelscanner.model.ProductSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenFoodFactsApiService {
    @GET("cgi/search.pl?search_simple=1&action=process&json=1")
    fun searchProductByName(@Query("search_terms") productName: String): Call<ProductSearchResponse>
}
