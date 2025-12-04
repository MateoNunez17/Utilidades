package es.fpsumma.dam2.utilidades.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import es.fpsumma.dam2.utilidades.data.local.AppDatabase
import es.fpsumma.dam2.utilidades.model.Asignatura
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareasViewModel(app: Application) : AndroidViewModel(app) {

    // 1) Abrimos/creamos la base de datos donde se guardan las tareas (archivo "tareas.db")
    private val db = Room.databaseBuilder(
        app, AppDatabase::class.java, "tareas.db"
    ).fallbackToDestructiveMigration(false).build()

    // 2) El DAO es quien hace las operaciones con la base de datos (guardar, borrar, leer...)
    private val dao = db.asignaturaDao()

    // 3) Lista de tareas “observable”: cuando la BD cambia, esto se actualiza y la UI se entera
    val asignaturas: StateFlow<List<Asignatura>> =
        dao.getAllAsignatura().stateIn(
            viewModelScope,              // se ejecuta mientras exista el ViewModel
            SharingStarted.Lazily,       // empieza cuando alguien lo está viendo
            emptyList()                  // valor inicial (al principio no hay nada)
        )

    // 4) Añadir una asignatura (se hace en segundo plano para no bloquear la app)
    fun addAsignatura(titulo: String, nota: Int) = viewModelScope.launch {
        dao.insert(Asignatura(titulo = titulo, nota = nota))
    }

    // 5) Borrar una asignatura (también en segundo plano)
    fun deleteAsigantura(asignatura: Asignatura) = viewModelScope.launch {
        dao.delete(asignatura)
    }

}