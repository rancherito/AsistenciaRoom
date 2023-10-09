package com.app.taxi.dao_taxi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.app.taxi.models.Viajes

@Dao
interface ViajesDao {
    @Query("SELECT * FROM Viajes")
    fun getAll(): List<Viajes>

    @Insert
    fun insert(viaje: Viajes)

    @Update
    fun update(viaje: Viajes)

    @Delete
    fun delete(viaje: Viajes)
}
