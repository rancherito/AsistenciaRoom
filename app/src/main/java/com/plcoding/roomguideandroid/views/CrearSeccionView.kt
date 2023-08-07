package com.plcoding.roomguideandroid.views

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
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.roomguideandroid.AsistenciaViewModel
import com.plcoding.roomguideandroid.Seccion
import com.plcoding.roomguideandroid.Ventana
import com.plcoding.roomguideandroid.dao.CursoSeccionesModel
import java.util.UUID


enum class EstadoCreacion {
    LISTA,
    SELECCIONAR,
    CREAR,
}

@Composable
fun CrearSeccionView(viewModel: AsistenciaViewModel) {

    //search
    val buscardor = remember { mutableStateOf("") }

    val cursos = viewModel.cursos.collectAsState(emptyList())
    val cursoSecciones = viewModel.cursoSecciones.collectAsState(emptyList())
    val estado = remember { mutableStateOf(EstadoCreacion.LISTA) }

    val curso_id_actual = remember { mutableStateOf("") }


    val options = listOf("A", "B", "C", "D")
    val selectedOption = remember { mutableStateOf(options[0]) }

    viewModel.cargarCursos()
    viewModel.cargarSecciones()
    if (estado.value == EstadoCreacion.LISTA) {
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
                        Icon(Icons.Filled.Home, null)
                    }
                    Text(
                        "Control Secciones",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = {
                            estado.value = EstadoCreacion.SELECCIONAR
                        }
                    ) {
                        Text("Crear")
                    }
                }
            }
            item {
                if (cursoSecciones.value.isEmpty()) {
                    Text(
                        text = "No hay secciones creadas",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
            items(cursoSecciones.value) { sec ->
                SeccionCard(sec)
            }
        }
    } else if (estado.value == EstadoCreacion.SELECCIONAR) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
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
                    "Crear docente",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        estado.value = EstadoCreacion.LISTA
                    }
                ) {
                    Text("Regresar")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = buscardor.value, onValueChange = { buscardor.value = it },
                placeholder = {
                    Text(text = "Buscar curso")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cursos.value.filter { x ->
                    buscardor.value == "" || x.codigo.contains(
                        buscardor.value
                    ) || x.nombre.contains(buscardor.value)
                }) { curso ->

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            //icono libro
                            Icon(Icons.Filled.PlayArrow, contentDescription = null)
                            Text(curso.nombre, modifier = Modifier.weight(1f))
                            Text(curso.codigo)
                            Button(onClick = {
                                curso_id_actual.value = curso.curso_id
                                estado.value = EstadoCreacion.CREAR
                            }) {
                                Icon(Icons.Filled.Add, contentDescription = null)
                            }
                        }
                    }

                }
            }

        }
    } else if (estado.value == EstadoCreacion.CREAR) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Seleccionar sección",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        estado.value = EstadoCreacion.SELECCIONAR
                    }
                ) {
                    Text("Regresar")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            options.forEach { text ->
                Card {
                    Row((
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (text == selectedOption.value),
                                    onClick = { selectedOption.value = text }
                                ).padding(8.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "SECCIÓN ${text}",
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        RadioButton(
                            selected = (text == selectedOption.value),
                            onClick = { selectedOption.value = text }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                viewModel.agregarSeccion(
                    Seccion(
                        curso_id = curso_id_actual.value,
                        seccion_codigo = selectedOption.value,
                        seccion_id = UUID.randomUUID().toString()
                    )
                )
                estado.value = EstadoCreacion.LISTA
            }) {
                Text("Crear")
            }
        }

    }
}

@Composable
private fun SeccionCard(cursoSeccionesModel: CursoSeccionesModel) {
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
                text = cursoSeccionesModel.codigo,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = cursoSeccionesModel.nombre,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
            Text(
                text = cursoSeccionesModel.seccion_codigo,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun RadioButtonGroup() {
    val options = listOf("Sección A", "Sección B", "Sección C", "Sección D")
    val selectedOption = remember { mutableStateOf(options[0]) }

    Column {
        options.forEach { text ->
            Row(
                Modifier
                    .selectable(
                        selected = (text == selectedOption.value),
                        onClick = { selectedOption.value = text }
                    )
            ) {
                RadioButton(
                    selected = (text == selectedOption.value),
                    onClick = { selectedOption.value = text }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun RadioButtonGroupPreview() {
    RadioButtonGroup()
}