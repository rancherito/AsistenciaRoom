package com.plcoding.roomguideandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.plcoding.roomguideandroid.ui.theme.RoomGuideAndroidTheme
import com.plcoding.roomguideandroid.views.AdminView
import com.plcoding.roomguideandroid.views.CrearCursoView
import com.plcoding.roomguideandroid.views.LoginView

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            DBAsistencia::class.java,
            "asistencidb_02"
        ).build()
    }
    private val viewModel by viewModels<AsistenciaViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AsistenciaViewModel(
                        contactDao = db.dao,
                        cursoDAO = db.cursoDAO(),
                        seccionDAO = db.seccionDAO(),
                        seccionDocenteDAO = db.seccionDocenteDAO(),
                        docenteDAO = db.docenteDAO(),
                        alumnoDAO = db.alumnoDAO(),
                        seccionAlumnoDAO = db.seccionAlumnoDAO(),
                        asistenciaDAO = db.asistenciaDAO(),
                    ) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomGuideAndroidTheme {

                viewModel.state.collectAsState()
                //START DATOS TEMPORALES
               val users = listOf(
                    User(1, "admin", "admin", "admin"),
                    User(2, "docente", "docente", "docente")
                )
                //cargar ventanaActiva desde viewModel
                val ventana by viewModel.ventanaActiva.collectAsState()
                when (ventana) {
                    Ventana.LOGIN -> {
                        LoginView(users = users, viewModel = viewModel)
                    }
                    Ventana.ADMIN -> {
                        AdminView(viewModel = viewModel)
                    }
                    Ventana.CREAR_CURSO -> {
                        CrearCursoView(viewModel = viewModel)
                    }
                    else -> {
                        Text(text = "No se ha cargado la ventana")
                    }
                }




                                //ContactScreen(state = state, onEvent = viewModel::onEvent)


            }
        }
    }
}