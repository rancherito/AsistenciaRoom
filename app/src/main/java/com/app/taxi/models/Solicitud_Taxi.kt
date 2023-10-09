package com.app.taxi.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Cliente::class,
        parentColumns = ["cliente_id"],
        childColumns = ["cliente_id"]
    ),
    ForeignKey(entity = Chofer::class, parentColumns = ["chofer_id"], childColumns = ["chofer_id"])
    // Considera agregar la referencia a la ruta aquí si es necesario.
])
data class Solicitud_Taxi(
    @PrimaryKey val solicitud_id: String,
    val cliente_id: String,
    val chofer_id: String,
    val ruta_id: String,
    val estado: String
)