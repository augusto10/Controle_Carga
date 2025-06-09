package com.esplendor.cargoscan.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esplendor.cargoscan.data.entities.Control
import com.esplendor.cargoscan.data.repository.CargoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlsViewModel @Inject constructor(
    private val repository: CargoRepository
) : ViewModel() {
    private val _controls = MutableStateFlow<List<Control>>(emptyList())
    val controls: StateFlow<List<Control>> = _controls

    private val _selectedControl = MutableStateFlow<Control?>(null)
    val selectedControl: StateFlow<Control?> = _selectedControl

    init {
        loadControls()
    }

    private fun loadControls() {
        viewModelScope.launch {
            repository.getAllControls().collect { controls ->
                _controls.value = controls
            }
        }
    }

    fun createNewControl(
        motoristaNome: String,
        motoristaCpf: String,
        responsavelNome: String,
        responsavelCpf: String
    ) {
        viewModelScope.launch {
            val newControl = Control(
                motoristaNome = motoristaNome,
                motoristaCpf = motoristaCpf,
                responsavelNome = responsavelNome,
                responsavelCpf = responsavelCpf
            )
            repository.insertControl(newControl)
        }
    }

    fun selectControl(control: Control) {
        _selectedControl.value = control
    }

    fun updateControl(control: Control) {
        viewModelScope.launch {
            repository.updateControl(control)
        }
    }

    fun deleteControl(control: Control) {
        viewModelScope.launch {
            repository.deleteControl(control)
        }
    }

    fun associateNFeWithControl(nfeCodigo: String) {
        selectedControl.value?.let { control ->
            viewModelScope.launch {
                repository.associateNFeWithControl(nfeCodigo, control.id)
            }
        }
    }

    fun createManifestoPdf(control: Control, nfes: List<NFe>): File {
        val pdfManager = PdfManager(LocalContext.current)
        return pdfManager.createManifestoPdf(control, nfes)
    }
}
