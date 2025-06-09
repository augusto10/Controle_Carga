package com.esplendor.cargoscan.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esplendor.cargoscan.data.PdfManager
import com.esplendor.cargoscan.data.entities.Control
import com.esplendor.cargoscan.ui.viewmodels.ControlsViewModel
import java.io.File

@Composable
fun SignatureScreen(
    viewModel: ControlsViewModel,
    control: Control,
    onBack: () -> Unit,
    onSave: (File) -> Unit
) {
    var signaturePath by remember { mutableStateOf<Path?>(null) }
    var signatureBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val pdfManager = remember { PdfManager(LocalContext.current) }
    val nfes = viewModel.getNFesByControl(control.id).collectAsState(initial = emptyList()).value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assinar Manifesto") },
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
                text = "Assine abaixo para confirmar o recebimento",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
            ) {
                drawRect(
                    color = Color.LightGray,
                    size = size
                )
                
                signaturePath?.let { path ->
                    drawPath(
                        path = path,
                        color = Color.Black,
                        style = Stroke(width = 10f)
                    )
                }
            }

            Button(
                onClick = {
                    // Aqui você implementaria a lógica para converter o canvas em Bitmap
                    // Por enquanto, vamos usar um placeholder
                    val file = pdfManager.createManifestoPdf(control, nfes, null)
                    onSave(file)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = signaturePath != null
            ) {
                Text("Salvar Manifesto")
            }
        }
    }
}
