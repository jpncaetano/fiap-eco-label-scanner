package br.com.fiap.ecolabelscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.fiap.ecolabelscanner.navigation.NavGraph
import br.com.fiap.ecolabelscanner.ui.theme.EcoLabelScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLabelScannerTheme {
                NavGraph()
            }
        }
    }
}
