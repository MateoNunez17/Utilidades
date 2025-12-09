package es.fpsumma.dam2.utilidades.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.fpsumma.dam2.utilidades.model.Tarea
import kotlinx.coroutines.flow.Flow


@Dao
interface TareaDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tarea: Tarea)

    @Update
    suspend fun update(tarea: Tarea)

    @Delete
    suspend fun delete(tarea: Tarea)
.
    @Query("SELECT * from tareas WHERE id = :id")
    fun getTarea(id: Int): Flow<Tarea>

    @Query("SELECT * from tareas ORDER BY titulo ASC")
    fun getAllTareas(): Flow<List<Tarea>>
}