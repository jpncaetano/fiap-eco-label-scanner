package br.com.fiap.ecolabelscanner.data

import br.com.fiap.ecolabelscanner.model.SearchResponse
import br.com.fiap.ecolabelscanner.network.RetrofitInstance
import retrofit2.Call

class ProductRepository {

    private val api = RetrofitInstance.api

    // Busca um produto pelo código de barras
    fun searchProductByBarcode(barcode: String): Call<SearchResponse> {
        return api.searchProductByBarcode(barcode)
    }

    // Busca produtos pelo nome
    fun searchProductsByName(query: String): Call<SearchResponse> {
        return api.searchProductByName(query)
    }
}
