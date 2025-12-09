package es.fpsumma.dam2.utilidades.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.fpsumma.dam2.utilidades.model.Asignatura

@Database(entities = [Asignatura::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun asignaturaDao(): AsignaturaDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return Instance ?: synchronized(this) {

                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "utilidades"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}