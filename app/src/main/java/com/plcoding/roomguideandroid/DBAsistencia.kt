package com.plcoding.roomguideandroid

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.roomguideandroid.dao.AlumnoDAO
import com.plcoding.roomguideandroid.dao.ContactDao
import com.plcoding.roomguideandroid.dao.SeccionDAO

@Database(
    entities = [
        Curso::class,
        Seccion::class,
        SeccionDocente::class,
        Docente::class,
        Alumno::class,
        SeccionAlumno::class,
        Asistencia::class,
        Contact::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DBAsistencia : RoomDatabase() {

    abstract val dao: ContactDao

    abstract fun cursoDAO(): CursoDAO
    abstract fun seccionDAO(): SeccionDAO
    abstract fun seccionDocenteDAO(): SeccionDocenteDAO
    abstract fun docenteDAO(): DocenteDAO
    abstract fun alumnoDAO(): AlumnoDAO
    abstract fun seccionAlumnoDAO(): SeccionAlumnoDAO
    abstract fun asistenciaDAO(): AsistenciaDAO
}