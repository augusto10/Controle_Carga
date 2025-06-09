package com.esplendor.cargoscan.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.esplendor.cargoscan.data.entities.PrintOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val _printOptions = MutableStateFlow(loadPrintOptions())
    val printOptions: Flow<PrintOptions> = _printOptions

    private fun loadPrintOptions(): PrintOptions {
        return PrintOptions(
            printerName = prefs.getString("printer_name", "") ?: "",
            paperSize = prefs.getString("paper_size", "A4") ?: "A4"
        )
    }

    fun savePrintOptions(options: PrintOptions) {
        prefs.edit().apply {
            putString("printer_name", options.printerName)
            putString("paper_size", options.paperSize)
        }.apply()
        _printOptions.value = options
    }

    fun getPrintOptions(): PrintOptions {
        return _printOptions.value
    }
}
