package com.plcoding.roomguideandroid.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ruta(
    @PrimaryKey val ruta_id: String,
    val descripcion: String,
    val intervalo_llegada: String,
    val intervalo_salida: String
)