package br.com.fiap.ecolabelscanner.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import br.com.fiap.ecolabelscanner.model.FavoriteProduct
import kotlinx.coroutines.flow.map

class FavoriteRepository(private val favoriteProductDao: FavoriteProductDao) {

    val allFavorites: Flow<List<FavoriteProduct>> = favoriteProductDao.getAllFavorites()

    suspend fun insert(favoriteProduct: FavoriteProduct) {
        favoriteProductDao.insertFavorite(favoriteProduct)
    }

    suspend fun remove(favoriteProduct: FavoriteProduct) {
        val product = favoriteProductDao.getFavoriteById(favoriteProduct.id ?: return).firstOrNull()
        product?.let {
            favoriteProductDao.removeFavorite(it)
        }
    }

    fun isFavorite(barcode: String): Flow<Boolean> {
        return favoriteProductDao.getFavoriteByBarcode(barcode).map { it != null }
    }

    fun getFavoriteByBarcode(barcode: String): Flow<FavoriteProduct?> {
        return favoriteProductDao.getFavoriteByBarcode(barcode)
    }
}
