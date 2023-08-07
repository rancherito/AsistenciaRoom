package com.plcoding.roomguideandroid.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.plcoding.roomguideandroid.AsistenciaViewModel
import com.plcoding.roomguideandroid.User
import com.plcoding.roomguideandroid.Ventana


//crea un componente para el admin
@Composable
fun AdminView(viewModel: AsistenciaViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Panel administrativo", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { viewModel.cambiarVentana(Ventana.CREAR_CURSO) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar cursos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.cambiarVentana(Ventana.CREAR_DOCENTE) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar docentes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.cambiarVentana(Ventana.CREAR_ALUMNO) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar alumnos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.cambiarVentana(Ventana.CREAR_SECCION) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar secciones")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.cambiarVentana(Ventana.CREAR_SECCION_DOCENTE)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Docente Sección")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.cambiarVentana(Ventana.CREAR_MATRICULA_ALUMNO)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Matricular Alumno")
        }
    }
}


@Composable
fun DocenteView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Vista de Docente")
    }
}


@Composable
fun LoginView(
    users: List<User> = listOf(),
    viewModel: AsistenciaViewModel
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //make a text loginc center
        Text("Iniciar sesión", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            //check if user exist
            val userExist =
                users.find { it.usuario == username.value && it.contraseña == password.value }


            if (userExist != null) {
                //if user exist, check if is admin or docente
                if (userExist.rol == "admin") {
                    Toast.makeText(context, "Admin accedido", Toast.LENGTH_SHORT).show()
                    viewModel.cambiarVentana(1)
                } else {
                    Toast.makeText(context, "Docente accedido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "No se encontro  usuario", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Iniciar sesión")
        }
    }
}