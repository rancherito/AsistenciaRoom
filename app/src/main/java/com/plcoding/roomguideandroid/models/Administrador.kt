package com.plcoding.roomguideandroid.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Administrador(
    @PrimaryKey val admin_id: String,
    val nombre: String,
    val correo: String,
    val contraseña: String
)