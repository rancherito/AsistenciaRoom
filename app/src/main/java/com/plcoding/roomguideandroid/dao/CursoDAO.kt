package com.plcoding.roomguideandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface  CursoDAO {
    @Query("SELECT * FROM curso")
    fun getAll(): List<Curso>

    @Insert
    fun insertAll(vararg cursos: Curso)

    @Delete
    fun delete(curso: Curso)
}