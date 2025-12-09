package es.fpsumma.dam2.utilidades.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tareas")
data class Tarea(


    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "titulo")
    val titulo: String,

    @ColumnInfo(name = "descripcion")
    val descripcion: String,

    @ColumnInfo(name = "prioridad", defaultValue = "1")
    val prioridad: Int = 1,

    @ColumnInfo(name = "categoria")
    val categoria: String? = null
)