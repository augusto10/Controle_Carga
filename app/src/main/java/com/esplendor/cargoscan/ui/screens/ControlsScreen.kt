package com.esplendor.cargoscan.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esplendor.cargoscan.data.entities.Control
import kotlinx.coroutines.launch

@Composable
fun ControlsScreen(
    onControlSelected: (Control) -> Unit,
    onNewControl: () -> Unit
) {
    var controls by remember { mutableStateOf<List<Control>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Aqui você implementaria a lógica para carregar os controles do banco de dados
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewControl,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Novo Controle")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Controles de Carga",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(controls) { control ->
                    ControlCard(
                        control = control,
                        onSelected = { onControlSelected(control) }
                    )
                }
            }
        }
    }
}

@Composable
fun ControlCard(
    control: Control,
    onSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onSelected() },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Motorista: ${control.motoristaNome}",
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "Data: ${control.dataCriacao}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Status: ${control.status}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}
