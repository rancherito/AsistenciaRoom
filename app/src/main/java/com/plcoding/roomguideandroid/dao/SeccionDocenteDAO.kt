package com.plcoding.roomguideandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SeccionDocenteDAO {
    @Query("SELECT * FROM SeccionDocente")
    fun getAll(): List<SeccionDocente>

    @Insert
    fun insertAll(vararg seccionDocentes: SeccionDocente)

    @Delete
    fun delete(seccionDocente: SeccionDocente)
}