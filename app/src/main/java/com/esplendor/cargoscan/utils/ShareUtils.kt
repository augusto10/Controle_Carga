package com.esplendor.cargoscan.utils

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.esplendor.cargoscan.data.entities.Control
import java.io.File

class ShareUtils(
    private val activity: ComponentActivity
) {
    private val shareLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    fun sharePdf(file: File, control: Control) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            putExtra(Intent.EXTRA_SUBJECT, "Manifesto de Carga - ${control.motoristaNome}")
            putExtra(Intent.EXTRA_TEXT, "Manifesto de Carga gerado em ${control.dataCriacao}")
        }

        activity.startActivity(Intent.createChooser(intent, "Compartilhar Manifesto"))
    }

    fun shareViaWhatsApp(file: File, control: Control, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            putExtra(Intent.EXTRA_TEXT, "Manifesto de Carga gerado em ${control.dataCriacao}")
            setPackage("com.whatsapp")
        }

        activity.startActivity(intent)
    }
}

@Composable
fun rememberShareUtils(): ShareUtils {
    val context = LocalContext.current as ComponentActivity
    return remember {
        ShareUtils(context)
    }
}
