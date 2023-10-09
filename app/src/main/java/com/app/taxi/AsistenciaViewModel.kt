package com.app.taxi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class AsistenciaViewModel(
    val db_taxi: DB_taxi
) : ViewModel() {


}