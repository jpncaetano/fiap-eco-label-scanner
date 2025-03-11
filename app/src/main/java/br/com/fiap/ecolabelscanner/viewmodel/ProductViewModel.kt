package br.com.fiap.ecolabelscanner.viewmodel

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

    var products: List<Product>? = null
    var errorMessage: String? = null

    fun searchProduct(name: String, onResult: (List<Product>?) -> Unit) {
        viewModelScope.launch {
            val call = RetrofitInstance.api.searchProductByName(name)
            call.enqueue(object : Callback<ProductSearchResponse> {
                override fun onResponse(
                    call: Call<ProductSearchResponse>,
                    response: Response<ProductSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        products = response.body()?.products
                        onResult(products)
                    } else {
                        errorMessage = "Erro ao buscar produto"
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<ProductSearchResponse>, t: Throwable) {
                    errorMessage = t.message
                    onResult(null)
                }
            })
        }
    }
}
