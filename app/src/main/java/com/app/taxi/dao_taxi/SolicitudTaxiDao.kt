package com.app.taxi.dao_taxi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.taxi.models.Solicitud_Taxi

@Dao
interface SolicitudTaxiDao {
    @Query("SELECT * FROM Solicitud_Taxi")
    fun getAll(): List<Solicitud_Taxi>

    @Insert
    fun insert(solicitud: Solicitud_Taxi)

    @Update
    fun update(solicitud: Solicitud_Taxi)

    @Delete
    fun delete(solicitud: Solicitud_Taxi)
}