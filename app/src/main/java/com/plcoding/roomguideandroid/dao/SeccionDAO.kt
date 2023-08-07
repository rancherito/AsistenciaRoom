package com.plcoding.roomguideandroid.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.plcoding.roomguideandroid.Seccion

@Dao
interface SeccionDAO {
    @Query("""
        SELECT s.seccion_id, s.curso_id, s.seccion_codigo, c.nombre, c.codigo
        FROM Seccion s
        JOIN Curso c ON s.curso_id = c.curso_id
        """)
    fun cursoSeccionesGetAll(): List<CursoSecciones>

    @Query("SELECT * FROM Seccion")
    fun getAll(): List<Seccion>

    @Insert
    fun insertAll(vararg secciones: Seccion)

    @Delete
    fun delete(seccion: Seccion)
}

data class CursoSecciones(
    val seccion_id: String,
    val curso_id: String,
    val seccion_codigo: String,
    val nombre: String,
    val codigo: String
)