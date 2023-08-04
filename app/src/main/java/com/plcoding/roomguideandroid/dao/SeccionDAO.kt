package com.plcoding.roomguideandroid.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.plcoding.roomguideandroid.Seccion

@Dao
interface SeccionDAO {
    @Query("SELECT * FROM Seccion")
    fun getAll(): List<Seccion>

    @Insert
    fun insertAll(vararg secciones: Seccion)

    @Delete
    fun delete(seccion: Seccion)
}