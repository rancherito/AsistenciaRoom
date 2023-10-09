package com.app.taxi.dao_taxi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.taxi.models.Vehiculo

@Dao
interface VehiculoDao {
    @Query("SELECT * FROM Vehiculo")
    fun getAll(): List<Vehiculo>

    @Insert
    fun insert(vehiculo: Vehiculo)

    @Update
    fun update(vehiculo: Vehiculo)

    @Delete
    fun delete(vehiculo: Vehiculo)
}