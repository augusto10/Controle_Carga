package com.esplendor.cargoscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esplendor.cargoscan.ui.screens.ControlsScreen
import com.esplendor.cargoscan.ui.screens.ManifestScreen
import com.esplendor.cargoscan.ui.screens.ScanningScreen
import com.esplendor.cargoscan.ui.theme.CargoScanTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CargoScanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "controls") {
        composable("controls") {
            val viewModel = hiltViewModel<ControlsViewModel>()
            ControlsScreen(
                viewModel = viewModel,
                onNewControl = { 
                    navController.navigate("new_control")
                },
                onControlSelected = { control ->
                    navController.navigate("select_nfe/${control.id}")
                }
            )
        }
        
        composable("new_control") {
            NewControlScreen(
                onControlCreated = { navController.popBackStack() }
            )
        }
        
        composable("select_nfe/{controlId}") { backStackEntry ->
            val controlId = backStackEntry.arguments?.getString("controlId")?.toLongOrNull()
            val viewModel = hiltViewModel<ControlsViewModel>()
            
            controlId?.let { id ->
                SelectNFeScreen(
                    controlId = id,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNFeSelected = { navController.navigate("manifest/${id}") }
                )
            }
        }
        
        composable("manifest/{controlId}") { backStackEntry ->
            val controlId = backStackEntry.arguments?.getString("controlId")?.toLongOrNull()
            val viewModel = hiltViewModel<ControlsViewModel>()
            
            controlId?.let { id ->
                ManifestScreen(
                    controlId = id,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onSave = { file ->
                        // Implementar lógica de compartilhamento aqui
                    }
                )
            }
        }
        
        composable("signature/{controlId}") { backStackEntry ->
            val controlId = backStackEntry.arguments?.getString("controlId")?.toLongOrNull()
            val viewModel = hiltViewModel<ControlsViewModel>()
            
            controlId?.let { id ->
                val control = viewModel.getAllControls().collectAsState(initial = emptyList()).value
                    .firstOrNull { it.id == id }
                
                control?.let { 
                    SignatureScreen(
                        viewModel = viewModel,
                        control = control,
                        onBack = { navController.popBackStack() },
                        onSave = { file ->
                            // Implementar compartilhamento aqui
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
