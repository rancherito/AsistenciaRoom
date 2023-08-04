package com.plcoding.roomguideandroid.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.plcoding.roomguideandroid.Alumno
import com.plcoding.roomguideandroid.AsistenciaViewModel
import com.plcoding.roomguideandroid.Ventana
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CrearAlumnoView(viewModel: AsistenciaViewModel) {
    val codigo = remember { mutableStateOf("") }
    val nombre = remember { mutableStateOf("") }
    val estaCreando = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val alumnos = viewModel.alumnos.collectAsState(emptyList())
    scope.launch(Dispatchers.IO) {
        viewModel.cargarAlumnos()
    }

    if (!estaCreando.value) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.cambiarVentana(Ventana.ADMIN) },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(Icons.Filled.Home,null)
                    }
                    Text(
                        "Control Alumnos",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = {
                            estaCreando.value = true
                        }
                    ) {
                        Text("Crear")
                    }
                }
            }
            item{
                if (alumnos.value.isEmpty()) {
                    Text(
                        text = "No hay alumnos",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h6
                    )
                }
            }

            items(alumnos.value) { al ->
                AlumnoCard(al)
            }
        }

    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Crear curso",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        estaCreando.value = false
                    }
                ) {
                    Text("Regresar")
                }
            }
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

                    val alumno = Alumno(
                        codigo = codigo.value,
                        nombres = nombre.value,
                        alumno_id = UUID.randomUUID().toString()
                    )
                    viewModel.agregarAlumno(alumno)
                    codigo.value = ""
                    nombre.value = ""
                    estaCreando.value = false

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear")
            }
        }
    }
}


@Composable
private fun AlumnoCard(alumno: Alumno) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = alumno.nombres,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = alumno.codigo,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        }
    }
}