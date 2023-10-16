package com.app.taxi

import com.app.taxi.models.Cliente
import io.github.serpro69.kfaker.Faker
import java.util.UUID

class Utils{

    companion object {
        fun crearUsuarioAleatorio(): Cliente {
            val faker = Faker()

            val id = UUID.randomUUID().toString()
            val name = faker.name.name()
            val email = faker.internet.email()
            val password = generatePassword(16)

            return Cliente(
                cliente_id = id,
                nombre = name,
                correo = email,
                contraseña = password
            )
        }

        fun generatePassword(length: Int = 10): String {
            val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
}

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
        const val CREAR_SECCION_DOCENTE = 7
        const val CREAR_MATRICULA_ALUMNO = 8
    }
}