package es.fpsumma.dam2.utilidades.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.fpsumma.dam2.utilidades.model.Asignatura
import kotlinx.coroutines.flow.Flow

// @Dao indica que esta interfaz es un DAO de Room:
// aquí declaramos las operaciones típicas para la tabla "tareas".
@Dao
interface AsignaturaDao {

    // Inserta una tarea en la tabla.
    // onConflict = IGNORE significa:
    // si hay un conflicto (por ejemplo, mismo id), NO se inserta y no da error.
    // suspend => se llama desde una corrutina (para no bloquear la app).
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(asignatura: Asignatura)

    // Actualiza una tarea existente (mismo id). Cambia sus campos en la BD.
    @Update
    suspend fun update(asignatura: Asignatura)

    // Borra esa tarea de la BD.
    @Delete
    suspend fun delete(asignatura: Asignatura)

    // Devuelve la tarea cuyo id coincida con el parámetro.
    // :id es un parámetro que se sustituye por el valor que pasamos a la función.
    // Devuelve Flow => si esa tarea cambia en la BD, la UI puede enterarse automáticamente.
    @Query("SELECT * from asignaturas WHERE id = :id")
    fun getAsignatura(id: Int): Flow<Asignatura>

    // Devuelve todas las tareas ordenadas por título de la A a la Z.
    // Flow<List<Tarea>> => la lista se actualiza sola cuando se insertan/borran/actualizan tareas.
    @Query("SELECT * from asignaturas ORDER BY asginatura ASC")
    fun getAllAsignatura(): Flow<List<Asignatura>>
}