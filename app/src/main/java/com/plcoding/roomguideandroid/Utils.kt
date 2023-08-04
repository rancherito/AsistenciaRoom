package com.plcoding.roomguideandroid

data class User(
    val user_id: Int,
    val usuario: String,
    val contraseña: String,
    val rol: String
)

class Ventana {
    companion object {
        const val LOGIN = 0
        const val ADMIN = 1
        const val DOCENTE = 2
        const val CREAR_CURSO = 3
        const val CREAR_SECCION = 4
        const val CREAR_ALUMNO = 5
        const val CREAR_DOCENTE = 6
    }
}