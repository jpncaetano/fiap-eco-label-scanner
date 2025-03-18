package br.com.fiap.ecolabelscanner.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.ecolabelscanner.model.Product
import br.com.fiap.ecolabelscanner.model.ProductSearchResponse
import br.com.fiap.ecolabelscanner.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    fun searchProduct(query: String, onResult: (List<Product>) -> Unit) {
        viewModelScope.launch {
            val call: Call<ProductSearchResponse> = if (query.all { it.isDigit() }) {
                RetrofitInstance.api.searchProductByBarcode(query)
            } else {
                RetrofitInstance.api.searchProductByName(query)
            }

            call.enqueue(object : Callback<ProductSearchResponse> {
                override fun onResponse(
                    call: Call<ProductSearchResponse>,
                    response: Response<ProductSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        val products = when {
                            body?.products != null -> body.products
                            body?.product != null -> listOf(body.product)
                            else -> emptyList()
                        }
                        Log.d("API_RESPONSE", "Produtos retornados: $products")
                        onResult(products)
                    } else {
                        Log.e("API_ERROR", "Erro na requisição: ${response.errorBody()?.string()}")
                        onResult(emptyList())
                    }
                }

                override fun onFailure(call: Call<ProductSearchResponse>, t: Throwable) {
                    Log.e("API_ERROR", "Erro na conexão: ${t.message}")
                    onResult(emptyList())
                }
            })
        }
    }
}

