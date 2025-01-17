package com.app.taxi.dao_taxi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.taxi.models.Ruta

@Dao
interface RutaDao {
    @Query("SELECT * FROM Ruta")
    fun getAll(): List<Ruta>

    @Insert
    fun insert(ruta: Ruta)

    @Update
    fun update(ruta: Ruta)

    @Delete
    fun delete(ruta: Ruta)
}