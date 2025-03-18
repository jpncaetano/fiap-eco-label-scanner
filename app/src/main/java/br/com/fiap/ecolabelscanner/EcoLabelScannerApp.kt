package br.com.fiap.ecolabelscanner

import android.app.Application
import br.com.fiap.ecolabelscanner.data.AppDataBase
import br.com.fiap.ecolabelscanner.data.FavoriteRepository

class EcoLabelScannerApp : Application() {

    val database: AppDataBase by lazy { AppDataBase.getDatabase(this) }
    val repository: FavoriteRepository by lazy { FavoriteRepository(database.favoriteProductDao()) }

}
