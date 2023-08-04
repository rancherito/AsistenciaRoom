package com.plcoding.roomguideandroid.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.plcoding.roomguideandroid.Alumno

@Dao
interface AlumnoDAO {
    @Query("SELECT * FROM Alumno")
    fun getAll(): List<Alumno>

    @Insert
    fun insertAll(vararg alumnos: Alumno)

    @Delete
    fun delete(alumno: Alumno)
}