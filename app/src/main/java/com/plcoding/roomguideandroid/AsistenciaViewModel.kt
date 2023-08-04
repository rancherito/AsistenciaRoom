package com.plcoding.roomguideandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.roomguideandroid.dao.AlumnoDAO
import com.plcoding.roomguideandroid.dao.ContactDao
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

    private val _cursos = MutableStateFlow(emptyList<Curso>())
    val cursos = _cursos.asStateFlow()






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

    /*
        fun onEvent(event: ContactEvent) {
            when (event) {
                is ContactEvent.DeleteContact -> {
                    viewModelScope.launch {
                        contactDao.deleteContact(event.contact)
                    }
                }

                ContactEvent.HideDialog -> {
                    _state.update {
                        it.copy(
                            isAddingContact = false
                        )
                    }
                }

                ContactEvent.SaveContact -> {
                    val firstName = state.value.firstName
                    val lastName = state.value.lastName
                    val phoneNumber = state.value.phoneNumber

                    if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                        return
                    }

                    val contact = Contact(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber
                    )
                    viewModelScope.launch {
                        contactDao.upsertContact(contact)
                    }
                    _state.update {
                        it.copy(
                            isAddingContact = false,
                            firstName = "",
                            lastName = "",
                            phoneNumber = ""
                        )
                    }
                }

                is ContactEvent.SetFirstName -> {
                    _state.update {
                        it.copy(
                            firstName = event.firstName
                        )
                    }
                }

                is ContactEvent.SetLastName -> {
                    _state.update {
                        it.copy(
                            lastName = event.lastName
                        )
                    }
                }

                is ContactEvent.SetPhoneNumber -> {
                    _state.update {
                        it.copy(
                            phoneNumber = event.phoneNumber
                        )
                    }
                }

                ContactEvent.ShowDialog -> {
                    _state.update {
                        it.copy(
                            isAddingContact = true
                        )
                    }
                }

                is ContactEvent.SortContacts -> {
                    _sortType.value = event.sortType
                }
            }
        }
    */
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
}