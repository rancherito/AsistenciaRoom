package com.plcoding.roomguideandroid.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.plcoding.roomguideandroid.AsistenciaViewModel
import com.plcoding.roomguideandroid.Curso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CrearCursoView(
    viewModel: AsistenciaViewModel,
) {
    val codigo = remember { mutableStateOf("") }
    val nombre = remember { mutableStateOf("") }


    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val cursos = viewModel.cursos.collectAsState(emptyList())
    scope.launch(Dispatchers.IO) {
        viewModel.cargarCursos()
    }

    Column {
        //listar cursos usando lazyColumn
        LazyColumn(modifier = Modifier.height(300.dp)) {
            items(cursos.value) {
                Text(text = "${it.codigo} ${it.nombre}")
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crear curso", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(32.dp))


            OutlinedTextField(
                value = codigo.value,
                onValueChange = { codigo.value = it },
                label = { Text("Código") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nombre.value,
                onValueChange = { nombre.value = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    val curso = Curso(
                        codigo = codigo.value,
                        nombre = nombre.value,
                        curso_id = UUID.randomUUID().toString()
                    )
                    viewModel.agregarCurso(curso)
                    //limpiar campos
                    codigo.value = ""
                    nombre.value = ""

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Curso")
            }
        }
    }
}


@Composable
fun CrearSeccionView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /* Aquí puedes manejar el evento de crear sección */ }) {
            Text("Crear Sección")
        }
    }
}

@Composable
fun CrearAlumnoView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /* Aquí puedes manejar el evento de crear alumno */ }) {
            Text("Crear Alumno")
        }
    }
}

@Composable
fun CrearDocenteView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /* Aquí puedes manejar el evento de crear docente */ }) {
            Text("Crear Docente")
        }
    }
}