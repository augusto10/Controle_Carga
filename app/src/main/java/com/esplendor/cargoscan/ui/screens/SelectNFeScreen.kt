package com.esplendor.cargoscan.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esplendor.cargoscan.data.entities.NFe
import com.esplendor.cargoscan.ui.viewmodels.ControlsViewModel

@Composable
fun SelectNFeScreen(
    viewModel: ControlsViewModel,
    controlId: Long,
    onBack: () -> Unit,
    onNFeSelected: () -> Unit
) {
    var selectedNFes by remember { mutableStateOf<List<NFe>>(emptyList()) }
    val allNFes = viewModel.getAllNFe().collectAsState(initial = emptyList()).value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selecionar Notas Fiscais") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = onNFeSelected) {
                        Icon(Icons.Default.Check, contentDescription = "Confirmar")
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(allNFes) { nfe ->
                    NFeItem(
                        nfe = nfe,
                        isSelected = selectedNFes.any { it.codigoBarras == nfe.codigoBarras },
                        onToggleSelection = { isSelected ->
                            if (isSelected) {
                                selectedNFes = selectedNFes.filter { it.codigoBarras != nfe.codigoBarras }
                            } else {
                                selectedNFes = selectedNFes + nfe
                            }
                        }
                    )
                }
            }

            Button(
                onClick = {
                    selectedNFes.forEach { nfe ->
                        viewModel.associateNFeWithControl(nfe.codigoBarras)
                    }
                    onNFeSelected()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedNFes.isNotEmpty()
            ) {
                Text("Confirmar Seleção")
            }
        }
    }
}

@Composable
fun NFeItem(
    nfe: NFe,
    isSelected: Boolean,
    onToggleSelection: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = onToggleSelection
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Nota: ${nfe.numeroNota}",
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = "Valor: R$${"%.2f".format(nfe.valorTotal)}",
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = "Destinatário: ${nfe.destinatario}",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
