package com.app.taxi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.taxi.Utils.Companion.crearUsuarioAleatorio
import com.app.taxi.models.Cliente
import com.app.taxi.theme.RoomGuideAndroidTheme
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainActivity : ComponentActivity() {

    // Inyectamos el ViewModel con su fabricante
    private val viewModel by viewModels<AsistenciaViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AsistenciaViewModel(
                        db_taxi = DB_taxi.getDatabase(applicationContext)
                    ) as T
                }
            }
        }
    )

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Llamamos al tema de la aplicación
            RoomGuideAndroidTheme {
                val usuario = remember {
                    mutableStateOf("")
                }

                val password = remember {
                    mutableStateOf("")
                }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = usuario.value,
                    onValueChange = {usuario.value = it},
                    placeholder = {
                        Text(text = "Usuario")

                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {password.value = it},
                    placeholder = {
                        Text(text = "Contraseña")

                    },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {

                    }) {
                    Text(text = "ACCEDER")
                }
            }

            }
        }
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun CrearUuarioAleatorio(
        viewModel: AsistenciaViewModel
    ) {
        val coroutine = rememberCoroutineScope()

        // Creamos un estado mutable para almacenar la lista de clientes
        val lista_clientes = remember {
            mutableStateOf(listOf<Cliente>())
        }

        // Lanzamos una corrutina para obtener la lista de clientes desde la base de datos
        coroutine.launch {
            val lista = withContext(Dispatchers.IO) {
                viewModel.db_taxi.clienteDao().getAll()
            }
            lista_clientes.value = lista

        }

        // Creamos una lista de clientes
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Button(onClick = {
                    coroutine.launch {
                        val lista = withContext(Dispatchers.IO) {
                            viewModel.db_taxi.clienteDao().insert(crearUsuarioAleatorio())
                            viewModel.db_taxi.clienteDao().getAll()
                        }

                        lista_clientes.value = lista

                    }
                }) {
                    Text(text = "Crear usuario aleatorio")
                }
            }
            items(lista_clientes.value) {

                // Mostramos los datos de cada cliente en una tarjeta
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "ID: ${it.cliente_id}")
                        Text(text = "Nombre: ${it.nombre}")
                        Text(text = "Correo electrónico: ${it.correo}")
                        Text(text = "Contraseña: ${it.contraseña}")
                    }
                }
            }
            if (lista_clientes.value.isEmpty()) {
                item {
                    Text(text = "No hay usuarios")
                }
            }
            items(8) {
                Text(text = it.toString())
            }
        }
    }
}