package com.esplendor.cargoscan.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esplendor.cargoscan.ui.viewmodels.ControlsViewModel

@Composable
fun NewControlScreen(
    viewModel: ControlsViewModel,
    onControlCreated: () -> Unit
) {
    var motoristaNome by remember { mutableStateOf("") }
    var motoristaCpf by remember { mutableStateOf("") }
    var responsavelNome by remember { mutableStateOf("") }
    var responsavelCpf by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Controle") },
                navigationIcon = {
                    IconButton(onClick = { onControlCreated() }) {
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
            OutlinedTextField(
                value = motoristaNome,
                onValueChange = { motoristaNome = it },
                label = { Text("Nome do Motorista") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = motoristaCpf,
                onValueChange = { motoristaCpf = it },
                label = { Text("CPF do Motorista") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = responsavelNome,
                onValueChange = { responsavelNome = it },
                label = { Text("Nome do Responsável") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = responsavelCpf,
                onValueChange = { responsavelCpf = it },
                label = { Text("CPF do Responsável") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.createNewControl(
                        motoristaNome = motoristaNome,
                        motoristaCpf = motoristaCpf,
                        responsavelNome = responsavelNome,
                        responsavelCpf = responsavelCpf
                    )
                    onControlCreated()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Criar Controle")
            }
        }
    }
}
