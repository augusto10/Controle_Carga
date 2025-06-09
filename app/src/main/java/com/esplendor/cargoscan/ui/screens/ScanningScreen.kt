package com.esplendor.cargoscan.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun ScanningScreen(
    context: Context,
    activity: Activity,
    onBarcodeScanned: (String) -> Unit
) {
    var scannedData by remember { mutableStateOf<String?>(null) }
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
        if (intentResult != null) {
            if (intentResult.contents != null) {
                scannedData = intentResult.contents
                onBarcodeScanned(intentResult.contents)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                val integrator = IntentIntegrator(activity)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                integrator.setPrompt("Scan a barcode")
                integrator.setCameraId(0)
                integrator.setBeepEnabled(true)
                integrator.setBarcodeImageEnabled(true)
                val intent = integrator.createScanIntent()
                launcher.launch(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Scan Barcode")
        }

        if (scannedData != null) {
            Text(
                text = "Scanned: $scannedData",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
