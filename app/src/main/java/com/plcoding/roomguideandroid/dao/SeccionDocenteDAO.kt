package com.plcoding.roomguideandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SeccionDocenteDAO {
    @Query("SELECT * FROM SeccionDocente")
    fun getAll(): List<SeccionDocente>

    @Query("""
        SELECT s.seccion_id, d.docente_id, d.nombre AS docente_nombre, s.seccion_codigo, c.nombre AS curso_nombre, c.codigo AS curso_codigo FROM secciondocente sd
        JOIN seccion s ON s.seccion_id = sd.seccion_id
        JOIN curso c ON c.curso_id = s.curso_id
        JOIN docente d ON d.docente_id = sd.docente_id
    """)
    fun seccionDocenteModelGetAll(): List<SeccionDocenteModel>

    @Insert
    fun insertAll(vararg seccionDocentes: SeccionDocente)

    @Delete
    fun delete(seccionDocente: SeccionDocente)
}

data class  SeccionDocenteModel(
    val seccion_id: String,
    val docente_id: String,
    val docente_nombre: String,
    val seccion_codigo: String,
    val curso_nombre: String,
    val curso_codigo: String
)