package com.plcoding.roomguideandroid.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.plcoding.roomguideandroid.AsistenciaViewModel
import com.plcoding.roomguideandroid.Curso
import com.plcoding.roomguideandroid.SeccionDocente
import com.plcoding.roomguideandroid.SeccionDocenteModel
import com.plcoding.roomguideandroid.Ventana

@Composable
fun CrearDocenteSeccionView(viewModel: AsistenciaViewModel) {

    val seccionDocentesModel = viewModel.seccionesDocentesModel.collectAsState(emptyList())

    val estadoCreacion = remember {
        mutableStateOf(EstadoCreacion.LISTA)
    }

    viewModel.cargarSeccionesDocentes()

    if (estadoCreacion.value == EstadoCreacion.SELECCIONAR){

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            //agregar un titulo y un boton crear cursos dentro de un row
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
                        "Control Seccion Docente",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = {
                            //estaCreando.value = true
                        }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
            }
            item{
                if (seccionDocentesModel.value.isEmpty()) {
                    Text(
                        text = "No docentes asignados a ningun curso",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
            items(seccionDocentesModel.value) { seccionCurso ->
                SeccionDocente(seccionCurso)
            }
        }
    }
}


@Composable
private fun SeccionDocente(seccionDocenteModel: SeccionDocenteModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = seccionDocenteModel.curso_codigo,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = seccionDocenteModel.seccion_codigo,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = seccionDocenteModel.curso_nombre,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
                Text(
                    text = seccionDocenteModel.docente_nombre,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            }
        }
    }
}