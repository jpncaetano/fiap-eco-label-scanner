package br.com.fiap.ecolabelscanner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.ecolabelscanner.data.AppDatabase
import br.com.fiap.ecolabelscanner.data.FavoriteRepository
import br.com.fiap.ecolabelscanner.model.FavoriteProduct
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoriteRepository
    private val _favorites = MutableStateFlow<List<FavoriteProduct>>(emptyList())
    val favorites: StateFlow<List<FavoriteProduct>> = _favorites

    init {
        val dao = AppDatabase.getDatabase(application).favoriteProductDao()
        repository = FavoriteRepository(dao)

        viewModelScope.launch {
            repository.allFavorites.collect { lista ->
                _favorites.value = lista
            }
        }
    }

    fun addFavorite(product: FavoriteProduct) {
        viewModelScope.launch {
            repository.insert(product)
            _favorites.value = repository.allFavorites.first()
        }
    }

    fun removeFavorite(product: FavoriteProduct) {
        viewModelScope.launch {
            repository.delete(product)
            _favorites.value = repository.allFavorites.first()
        }
    }

    fun isFavorite(code: String): StateFlow<Boolean> {
        return _favorites
            .map { list -> list.any { it.code == code } }
            .stateIn(viewModelScope, SharingStarted.Lazily, false)
    }
}
