package com.plcoding.roomguideandroid

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)


@Entity
data class Curso(
    @PrimaryKey val curso_id: String,
    val codigo: String,
    val nombre: String
)
@Entity
data class Seccion(
    @PrimaryKey val seccion_id: String,
    val curso_id: String,
    val seccion_codigo: String
)
@Entity
data class SeccionDocente(
    @PrimaryKey val seccion_id: String,
    val docente_id: String
)
@Entity
data class Docente(
    @PrimaryKey val docente_id: String,
    val nombre: String
)
@Entity
data class Alumno(
    @PrimaryKey val alumno_id: String,
    val nombres: String,
    val codigo: String
)
@Entity
data class SeccionAlumno(
    @PrimaryKey val seccion_alumno_id: String,
    val alumno_id: String
)
@Entity
data class Asistencia(
    val horas: String,
    val fecha: String,
    val seccion_alumno_id: String,
    @PrimaryKey val asistencia_id: String
)