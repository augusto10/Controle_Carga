package com.esplendor.cargoscan.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esplendor.cargoscan.data.entities.Control
import com.esplendor.cargoscan.data.entities.NFe
import java.io.File
import com.itextpdf.text.*
import com.itextpdf.text.pdf.*

@Composable
fun ManifestScreen(
    viewModel: ControlsViewModel,
    controlId: Long,
    onBack: () -> Unit,
    onSave: (File) -> Unit
) {
    val control = viewModel.getAllControls().collectAsState(initial = emptyList()).value
        .firstOrNull { it.id == controlId }
    
    val nfes = viewModel.getNFesByControl(controlId).collectAsState(initial = emptyList()).value
    val shareUtils = rememberShareUtils()

    if (control == null) {
        Text("Controle não encontrado")
        return
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navegar para a tela de assinatura
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Assinar Manifesto")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {
                            val file = viewModel.createManifestoPdf(control, nfes)
                            shareUtils.sharePdf(file, control)
                        }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Compartilhar")
                    }
                    IconButton(
                        onClick = {
                            val file = viewModel.createManifestoPdf(control, nfes)
                            shareUtils.shareViaWhatsApp(file, control, control.motoristaCpf)
                        }
                    ) {
                        Icon(Icons.Default.Whatsapp, contentDescription = "Compartilhar no WhatsApp")
                    }
                    IconButton(
                        onClick = {
                            val printManager = LocalContext.current.getSystemService(PrintManager::class.java)
                            val printAdapter = ManifestoPrintAdapter(control, nfes)
                            val printAttributes = PrintAttributes.Builder()
                                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                                .build()
                            
                            printManager.print(
                                "Manifesto de Carga",
                                printAdapter,
                                printAttributes,
                                null
                            )
                        }
                    ) {
                        Icon(Icons.Default.Print, contentDescription = "Imprimir")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Manifesto de Carga",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp)
            )

            ControlInfo(control)
            NFeList(nfes)
        }
    }
}

@Composable
fun ControlInfo(control: Control) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Informações do Controle",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Motorista: ${control.motoristaNome}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "CPF Motorista: ${control.motoristaCpf}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Responsável: ${control.responsavelNome}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "CPF Responsável: ${control.responsavelCpf}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun NFeList(nfes: List<NFe>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Notas Fiscais",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            nfes.forEach { nfe ->
                Text(
                    text = "Nota: ${nfe.numeroNota} - Valor: R$${"%.2f".format(nfe.valorTotal)}",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
