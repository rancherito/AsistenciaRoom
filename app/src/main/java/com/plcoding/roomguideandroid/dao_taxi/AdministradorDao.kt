package com.plcoding.roomguideandroid.dao_taxi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.plcoding.roomguideandroid.models.Administrador

@Dao
interface AdministradorDao {
    @Query("SELECT * FROM Administrador")
    fun getAll(): List<Administrador>

    @Insert
    fun insert(administrador: Administrador)

    @Update
    fun update(administrador: Administrador)

    @Delete
    fun delete(administrador: Administrador)
}