package com.plcoding.roomguideandroid.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cliente(
    @PrimaryKey val cliente_id: String,
    val nombre: String,
    val correo: String,
    val contraseña: String
)