package br.com.fiap.ecolabelscanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.ecolabelscanner.data.ProductRepository
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.model.SearchResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val repository = ProductRepository()

    // Busca produtos pelo nome
    fun searchProductsByName(query: String) {
        _isLoading.value = true

        repository.searchProductsByName(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                _isLoading.value = false

                if (response.isSuccessful && response.body() != null) {
                    val produtosRecebidos = response.body()?.products ?: emptyList()
                    _products.value = produtosRecebidos
                    _errorMessage.value = null

                    println("✅ Produtos na ViewModel (tamanho ${produtosRecebidos.size}): $produtosRecebidos")
                } else {
                    _errorMessage.value = "Erro na API: ${response.code()}"
                    println("❌ Erro na API: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Erro na requisição: ${t.message}"
                println("❌ Falha na requisição: ${t.message}")
            }
        })
    }

    // Busca um produto pelo código de barras
    fun fetchProductByBarcode(barcode: String) {
        _isLoading.value = true

        repository.searchProductByBarcode(barcode).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val productData = response.body()?.product
                    if (productData != null) {
                        _products.value = listOf(productData)
                        _errorMessage.value = null
                        println("Produto encontrado: $productData")
                    } else {
                        _products.value = emptyList()
                        _errorMessage.value = "Produto não encontrado."
                        println("API respondeu, mas sem produto.")
                    }
                } else {
                    _errorMessage.value = "Erro na API: ${response.code()}"
                    println("Erro na API: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Erro na requisição: ${t.message}"
                println("Falha na requisição: ${t.message}")
            }
        })
    }
}
