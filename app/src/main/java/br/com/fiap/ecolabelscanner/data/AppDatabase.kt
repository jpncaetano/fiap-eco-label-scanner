package br.com.fiap.ecolabelscanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.ecolabelscanner.model.FavoriteProduct

@Database(entities = [FavoriteProduct::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Define o DAO para acessar os produtos favoritos
    abstract fun favoriteProductDao(): FavoriteProductDao

    companion object {
        // Instância única do banco de dados para evitar múltiplas criações
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Garante que apenas uma instância do banco seja criada
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "favorites_db" // Nome do banco de dados
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
