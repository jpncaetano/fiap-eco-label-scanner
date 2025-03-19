package br.com.fiap.ecolabelscanner.data

import br.com.fiap.ecolabelscanner.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val dao: FavoriteProductDao) {

    // Retorna todos os produtos favoritados como um Flow para atualizações em tempo real
    val allFavorites: Flow<List<FavoriteProduct>> = dao.getAllFavorites()

    // Insere um produto nos favoritos
    suspend fun insert(product: FavoriteProduct) {
        dao.insertFavorite(product)
    }

    // Remove um produto dos favoritos
    suspend fun delete(product: FavoriteProduct) {
        dao.deleteFavorite(product)
    }

    // Retorna um produto favorito pelo código de barras, caso exista
    suspend fun getFavoriteByCode(code: String): FavoriteProduct? {
        return dao.getFavoriteByCode(code)
    }
}
