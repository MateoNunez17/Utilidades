package es.fpsumma.dam2.utilidades.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.fpsumma.dam2.utilidades.data.local.AppDatabase
import es.fpsumma.dam2.utilidades.model.Asignatura
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareasViewModel(app: Application) : AndroidViewModel(app) {

    // ✅ CRÍTICO: Usa el método Singleton corregido en AppDatabase
    private val db = AppDatabase.getDatabase(app.applicationContext)

    // Adaptado a la nueva función de la BD
    private val dao = db.asignaturaDao()

    // Adaptado a la lista de Asignaturas
    val asignaturas: StateFlow<List<Asignatura>> =
        dao.getAllAsignatura().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun addAsignatura(nombre: String, nota: Int, trimestre: String) = viewModelScope.launch {
        dao.insert(Asignatura(asignatura = nombre, nota = nota, trimestre = trimestre))
    }

    fun deleteAsignatura(asignatura: Asignatura) = viewModelScope.launch {
        dao.delete(asignatura)
    }

}