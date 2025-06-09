package com.esplendor.cargoscan.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import com.esplendor.cargoscan.data.entities.Control
import com.esplendor.cargoscan.data.entities.NFe
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PdfManager(private val context: Context) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    fun createManifestoPdf(
        control: Control,
        nfes: List<NFe>,
        signatureBitmap: Bitmap? = null
    ): File {
        val file = File.createTempFile("manifesto", ".pdf", context.cacheDir)
        val document = PdfDocument()
        
        // Página 1 - Informações do Controle
        val pageInfo = PdfDocument.PageInfo.Builder(1000, 1400, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        
        drawHeader(canvas)
        drawControlInfo(canvas, control)
        drawNFeList(canvas, nfes)
        
        if (signatureBitmap != null) {
            drawSignature(canvas, signatureBitmap)
        }
        
        document.finishPage(page)
        
        // Salvar o PDF
        FileOutputStream(file).use { outputStream ->
            document.writeTo(outputStream)
        }
        document.close()
        
        return file
    }
    
    private fun drawHeader(canvas: Canvas) {
        val paint = Paint().apply {
            textSize = 40f
            color = android.graphics.Color.BLACK
        }
        
        canvas.drawText("Manifesto de Carga", 50f, 100f, paint)
    }
    
    private fun drawControlInfo(canvas: Canvas, control: Control) {
        val paint = Paint().apply {
            textSize = 30f
            color = android.graphics.Color.BLACK
        }
        
        val yStart = 150f
        val lineHeight = 40f
        var currentY = yStart
        
        canvas.drawText("Informações do Controle", 50f, currentY, paint.apply { textSize = 35f })
        currentY += lineHeight * 2
        
        canvas.drawText("Motorista: ${control.motoristaNome}", 50f, currentY, paint)
        currentY += lineHeight
        canvas.drawText("CPF Motorista: ${control.motoristaCpf}", 50f, currentY, paint)
        currentY += lineHeight
        canvas.drawText("Responsável: ${control.responsavelNome}", 50f, currentY, paint)
        currentY += lineHeight
        canvas.drawText("CPF Responsável: ${control.responsavelCpf}", 50f, currentY, paint)
        currentY += lineHeight
        canvas.drawText("Data: ${dateFormat.format(control.dataCriacao)}", 50f, currentY, paint)
    }
    
    private fun drawNFeList(canvas: Canvas, nfes: List<NFe>) {
        val paint = Paint().apply {
            textSize = 30f
            color = android.graphics.Color.BLACK
        }
        
        val yStart = 400f
        val lineHeight = 40f
        var currentY = yStart
        
        canvas.drawText("Notas Fiscais", 50f, currentY, paint.apply { textSize = 35f })
        currentY += lineHeight * 2
        
        nfes.forEach { nfe ->
            canvas.drawText("Nota: ${nfe.numeroNota}", 50f, currentY, paint)
            currentY += lineHeight
            canvas.drawText("Valor: R$${"%.2f".format(nfe.valorTotal)}", 50f, currentY, paint)
            currentY += lineHeight
            canvas.drawText("Destinatário: ${nfe.destinatario}", 50f, currentY, paint)
            currentY += lineHeight * 2
        }
    }
    
    private fun drawSignature(canvas: Canvas, signatureBitmap: Bitmap) {
        val paint = Paint().apply {
            textSize = 30f
            color = android.graphics.Color.BLACK
        }
        
        val yStart = 1200f
        val signatureWidth = 300f
        val signatureHeight = 100f
        
        canvas.drawText("Assinatura", 50f, yStart, paint)
        
        val signatureRect = Rect(
            50,
            yStart.toInt() + 50,
            (signatureWidth + 50).toInt(),
            (signatureHeight + yStart).toInt()
        )
        
        canvas.drawBitmap(
            signatureBitmap,
            null,
            signatureRect,
            paint
        )
    }
}
