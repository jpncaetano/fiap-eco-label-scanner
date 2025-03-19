package br.com.fiap.ecolabelscanner.data

import androidx.room.*
import br.com.fiap.ecolabelscanner.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {

    // Insere um produto nos favoritos, substituindo caso já exista
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: FavoriteProduct)

    // Remove um produto dos favoritos
    @Delete
    suspend fun deleteFavorite(product: FavoriteProduct)

    // Retorna todos os produtos favoritados como um Flow para atualizações em tempo real
    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): Flow<List<FavoriteProduct>>

    // Retorna um produto favorito pelo código de barras, caso exista
    @Query("SELECT * FROM favorite_products WHERE code = :code LIMIT 1")
    suspend fun getFavoriteByCode(code: String): FavoriteProduct?
}
