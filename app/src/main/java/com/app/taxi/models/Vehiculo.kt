package com.app.taxi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vehiculo(
    @PrimaryKey val vehiculo_id: String,
    val color: String,
    val modelo: String,
    val num_asientos: Int,
    val detalles_adicionales: String?
)