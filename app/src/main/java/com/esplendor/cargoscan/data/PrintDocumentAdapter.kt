package com.esplendor.cargoscan.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.pdf.PrintedPdfDocument
import com.esplendor.cargoscan.data.entities.Control
import com.esplendor.cargoscan.data.entities.NFe
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ManifestoPrintAdapter(
    private val control: Control,
    private val nfes: List<NFe>
) : PrintDocumentAdapter() {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private var file: File? = null

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback,
        extras: Bundle?
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback.onLayoutCancelled()
            return
        }

        try {
            file = createPdfFile(newAttributes)
            callback.onLayoutFinished(null, true)
        } catch (e: IOException) {
            callback.onLayoutFailed(e.message)
        }
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: FileOutputStream?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback?.onWriteCancelled()
            return
        }

        try {
            file?.inputStream()?.use { input ->
                destination?.write(input.readBytes())
            }
            callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
        } catch (e: IOException) {
            callback?.onWriteFailed(e.message)
        } finally {
            file?.delete()
        }
    }

    private fun createPdfFile(attributes: PrintAttributes): File {
        val file = File.createTempFile("manifesto", ".pdf")
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(
            attributes.mediaSize.widthMils / 10,
            attributes.mediaSize.heightMils / 10,
            1
        ).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        drawHeader(canvas)
        drawControlInfo(canvas, control)
        drawNFeList(canvas, nfes)

        document.finishPage(page)
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
        
        var currentY = 150f
        val lineHeight = 40f
        
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
        
        var currentY = 400f
        val lineHeight = 40f
        
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
}
