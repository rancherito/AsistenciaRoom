package com.plcoding.roomguideandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.roomguideandroid.dao.AlumnoDAO
import com.plcoding.roomguideandroid.dao.ContactDao
import com.plcoding.roomguideandroid.dao.DocenteDAO
import com.plcoding.roomguideandroid.dao.SeccionDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class AsistenciaViewModel(
    private val contactDao: ContactDao,
    private val cursoDAO: CursoDAO,
    private val seccionDAO: SeccionDAO,
    private val seccionDocenteDAO: SeccionDocenteDAO,
    private val docenteDAO: DocenteDAO,
    private val alumnoDAO: AlumnoDAO,
    private val seccionAlumnoDAO: SeccionAlumnoDAO,
    private val asistenciaDAO: AsistenciaDAO
) : ViewModel() {


    //GESTOR DE CURSOS
    //Lista privada de cursos
    private val _cursos = MutableStateFlow(emptyList<Curso>())
    //lista publica de cursos de solo lectura
    val cursos = _cursos.asStateFlow()
    //metodo para agregar un curso
    fun agregarCurso(curso: Curso) {
        viewModelScope.launch(Dispatchers.IO) {
            cursoDAO.insertAll(curso)
            _cursos.value = cursoDAO.getAll()
        }
    }
    fun cargarCursos() {
        viewModelScope.launch(Dispatchers.IO) {
            _cursos.value = cursoDAO.getAll()
        }
    }


    //GESTOR DE DOCENTES
    //Lista privada de docentes
    private val _docentes = MutableStateFlow(emptyList<Docente>())
    //lista publica de docentes de solo lectura
    val docentes = _docentes.asStateFlow()
    //metodo para agregar un docente
    fun agregarDocente(docente: Docente) {
        viewModelScope.launch(Dispatchers.IO) {
            docenteDAO.insertAll(docente)
            _docentes.value = docenteDAO.getAll()
        }
    }
    fun cargarDocentes() {
        viewModelScope.launch(Dispatchers.IO) {
            _docentes.value = docenteDAO.getAll()
        }
    }

    //GESTOR DE ALUMNOS
    //Lista privada de alumnos
    private val _alumnos = MutableStateFlow(emptyList<Alumno>())
    //lista publica de alumnos de solo lectura
    val alumnos = _alumnos.asStateFlow()

    //metodo para agregar un alumno
    fun agregarAlumno(alumno: Alumno) {
        viewModelScope.launch(Dispatchers.IO) {
            alumnoDAO.insertAll(alumno)
            _alumnos.value = alumnoDAO.getAll()
        }
    }
    fun cargarAlumnos() {
        viewModelScope.launch(Dispatchers.IO) {
            _alumnos.value = alumnoDAO.getAll()
        }
    }



    //general
    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _ventanaActiva = MutableStateFlow(Ventana.ADMIN)

    //implemetar para cambiar de vista
    val ventanaActiva = _ventanaActiva.asStateFlow()

    //metodo para cambiar de vista
    fun cambiarVentana(ventana: Int) {
        _ventanaActiva.value = ventana
    }


    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.FIRST_NAME -> contactDao.getContactsOrderedByFirstName()
                SortType.LAST_NAME -> contactDao.getContactsOrderedByLastName()
                SortType.PHONE_NUMBER -> contactDao.getContactsOrderedByPhoneNumber()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


}