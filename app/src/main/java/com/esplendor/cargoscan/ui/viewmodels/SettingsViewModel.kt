package com.esplendor.cargoscan.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esplendor.cargoscan.data.entities.PrintOptions
import com.esplendor.cargoscan.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    private val _printOptions = MutableStateFlow<PrintOptions>(PrintOptions())
    val printOptions: StateFlow<PrintOptions> = _printOptions

    init {
        loadPrintOptions()
    }

    private fun loadPrintOptions() {
        viewModelScope.launch {
            repository.getPrintOptions().collect { options ->
                _printOptions.value = options
            }
        }
    }

    fun savePrintOptions(options: PrintOptions) {
        viewModelScope.launch {
            repository.savePrintOptions(options)
        }
    }

    fun getPrintOptions(): PrintOptions {
        return _printOptions.value
    }
}
