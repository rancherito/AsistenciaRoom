package com.plcoding.roomguideandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SeccionAlumnoDAO {
    @Query("SELECT * FROM SeccionAlumno")
    fun getAll(): List<SeccionAlumno>

    @Insert
    fun insertAll(vararg seccionAlumnos: SeccionAlumno)

    @Delete
    fun delete(seccionAlumno: SeccionAlumno)
}