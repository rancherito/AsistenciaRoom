package com.plcoding.roomguideandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AsistenciaDAO {
    @Query("SELECT * FROM Asistencia")
    fun getAll(): List<Asistencia>

    @Insert
    fun insertAll(vararg asistencias: Asistencia)

    @Delete
    fun delete(asistencia: Asistencia)
}