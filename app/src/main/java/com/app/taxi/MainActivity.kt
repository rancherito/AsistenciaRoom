package com.app.taxi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
                    items(lista_clientes.value) { cliente ->

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
                                Text(text = "ID: ${cliente.cliente_id}")
                                Text(text = "Nombre: ${cliente.nombre}")
                                Text(text = "Correo electrónico: ${cliente.correo}")
                                Text(text = "Contraseña: ${cliente.contraseña}")
                            }
                        }
                    }
                    if (lista_clientes.value.isEmpty()) {
                        item {
                            Text(text = "No hay usuarios")
                        }
                    }
                }
            }
        }
    }

    private fun crearUsuarioAleatorio(): Cliente {
        val faker = Faker()

        val id = UUID.randomUUID().toString()
        val name = faker.name.name()
        val email = faker.internet.email()
        val password = generatePassword(16)

        return Cliente(
            cliente_id = id,
            nombre = name,
            correo = email,
            contraseña = password
        )
    }

    fun generatePassword(length: Int = 10): String {
        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}