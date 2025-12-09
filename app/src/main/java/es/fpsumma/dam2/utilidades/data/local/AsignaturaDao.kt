package es.fpsumma.dam2.utilidades.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.fpsumma.dam2.utilidades.model.Asignatura
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(asignatura: Asignatura)

    @Update
    suspend fun update(asignatura: Asignatura)

    @Delete
    suspend fun delete(asignatura: Asignatura)

    @Query("SELECT * from asignaturas WHERE id = :id")
    fun getAsignatura(id: Int): Flow<Asignatura>

    // Consulta todas las asignaturas
    @Query("SELECT * from asignaturas ORDER BY asignatura ASC")
    fun getAllAsignatura(): Flow<List<Asignatura>>
}