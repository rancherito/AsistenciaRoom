package com.app.taxi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chofer(
    @PrimaryKey val chofer_id: String,
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val vehiculo_id: String
)