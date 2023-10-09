package com.plcoding.roomguideandroid.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.plcoding.roomguideandroid.models.Chofer
import com.plcoding.roomguideandroid.models.Solicitud_Taxi

@Entity(foreignKeys = [
    ForeignKey(entity = Chofer::class, parentColumns = ["chofer_id"], childColumns = ["chofer_id"]),
    ForeignKey(entity = Solicitud_Taxi::class, parentColumns = ["solicitud_id"], childColumns = ["solicitud_id"])
])
data class Viajes(
    @PrimaryKey val viaje_id: String,
    val chofer_id: String,
    val solicitud_id: String,
    val fecha_inicio: String,
    val fecha_fin: String,
    val costo: Double
)
