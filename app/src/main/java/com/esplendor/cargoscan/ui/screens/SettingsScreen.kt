package com.esplendor.cargoscan.ui.screens

import android.print.PrintAttributes
import android.print.PrintManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.esplendor.cargoscan.ui.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    onBack: () -> Unit
) {
    var printOptions by remember { mutableStateOf(viewModel.getPrintOptions()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Configurações de Impressão",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = printOptions.printerName,
                onValueChange = { 
                    printOptions = printOptions.copy(printerName = it)
                    viewModel.savePrintOptions(printOptions)
                },
                label = { Text("Nome da Impressora") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tipo de Papel")
                DropdownMenu(
                    expanded = printOptions.isPaperTypeMenuExpanded,
                    onDismissRequest = { printOptions = printOptions.copy(isPaperTypeMenuExpanded = false) }
                ) {
                    listOf("A4", "A5", "Personalizado").forEach { size ->
                        DropdownMenuItem(
                            onClick = {
                                printOptions = printOptions.copy(
                                    paperSize = size,
                                    isPaperTypeMenuExpanded = false
                                )
                                viewModel.savePrintOptions(printOptions)
                            }
                        ) {
                            Text(size)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val printManager = LocalContext.current.getSystemService(PrintManager::class.java)
                    val printAttributes = PrintAttributes.Builder()
                        .setMediaSize(
                            when (printOptions.paperSize) {
                                "A4" -> PrintAttributes.MediaSize.ISO_A4
                                "A5" -> PrintAttributes.MediaSize.ISO_A5
                                else -> PrintAttributes.MediaSize.UNKNOWN
                            }
                        )
                        .build()
                    
                    printManager.print(
                        "Configurações",
                        PrintDocumentAdapter(),
                        printAttributes
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Testar Impressão")
            }
        }
    }
}

data class PrintOptions(
    val printerName: String = "",
    val paperSize: String = "A4",
    val isPaperTypeMenuExpanded: Boolean = false
)
