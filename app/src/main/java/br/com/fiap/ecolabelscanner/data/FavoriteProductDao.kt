package br.com.fiap.ecolabelscanner.data

import androidx.room.*
import br.com.fiap.ecolabelscanner.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {

    // Insere um produto nos favoritos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteProduct: FavoriteProduct)

    // Remove um produto dos favoritos
    @Delete
    suspend fun removeFavorite(favoriteProduct: FavoriteProduct)

    // Obtém todos os produtos favoritos
    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): Flow<List<FavoriteProduct>>

    // Verifica se um produto já está nos favoritos pelo código de barras
    @Query("SELECT * FROM favorite_products WHERE barcode = :barcode LIMIT 1")
    fun getFavoriteByBarcode(barcode: String): Flow<FavoriteProduct?>

    // Obtém um produto favorito pelo ID (para remoção)
    @Query("SELECT * FROM favorite_products WHERE id = :productId LIMIT 1")
    fun getFavoriteById(productId: Long): Flow<FavoriteProduct?>
}
