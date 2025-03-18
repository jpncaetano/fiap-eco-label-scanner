package br.com.fiap.ecolabelscanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.ecolabelscanner.data.FavoriteRepository
import br.com.fiap.ecolabelscanner.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    val allFavorites: Flow<List<FavoriteProduct>> = repository.allFavorites

    fun addFavorite(product: FavoriteProduct) {
        viewModelScope.launch {
            repository.insert(product)
        }
    }

    fun removeFavorite(product: FavoriteProduct) {
        viewModelScope.launch {
            repository.remove(product)
        }
    }

    fun isFavorite(barcode: String): Flow<Boolean> {
        return repository.getFavoriteByBarcode(barcode).map { it != null }
    }
}
