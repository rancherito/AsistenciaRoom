package com.app.taxi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.taxi.theme.RoomGuideAndroidTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

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

        DB_taxi.getDatabase(applicationContext)
        setContent {
            RoomGuideAndroidTheme {
                val courutina = rememberCoroutineScope()
                courutina.launch(Dispatchers.IO) {
                    viewModel.db_taxi.choferDao().getAll()
                }
                Text(text = "Hello World!")

            }
        }
    }
}