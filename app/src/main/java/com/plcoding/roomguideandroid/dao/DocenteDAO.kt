package com.plcoding.roomguideandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DocenteDAO {
    @Query("SELECT * FROM Docente")
    fun getAll(): List<Docente>

    @Insert
    fun insertAll(vararg docentes: Docente)

    @Delete
    fun delete(docente: Docente)
}