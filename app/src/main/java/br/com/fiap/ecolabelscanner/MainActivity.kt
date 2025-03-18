package br.com.fiap.ecolabelscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecolabelscanner.data.AppDataBase
import br.com.fiap.ecolabelscanner.data.FavoriteRepository
import br.com.fiap.ecolabelscanner.ui.screens.NavGraph
import br.com.fiap.ecolabelscanner.ui.theme.EcoLabelScannerTheme
import br.com.fiap.ecolabelscanner.viewmodel.FavoriteViewModel
import br.com.fiap.ecolabelscanner.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLabelScannerTheme {
                val context = LocalContext.current
                val database = remember { AppDataBase.getDatabase(context) }
                val repository = remember { FavoriteRepository(database.favoriteProductDao()) }
                val favoriteViewModel = remember { FavoriteViewModel(repository) }
                val productViewModel = remember { ProductViewModel() }
                val navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    productViewModel = productViewModel,
                    favoriteViewModel = favoriteViewModel
                )
            }
        }
    }
}
