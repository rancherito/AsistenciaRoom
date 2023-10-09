package com.app.taxi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.taxi.dao_taxi.AdministradorDao
import com.app.taxi.dao_taxi.ChoferDao
import com.app.taxi.dao_taxi.ClienteDao
import com.app.taxi.dao_taxi.RutaDao
import com.app.taxi.dao_taxi.SolicitudTaxiDao
import com.app.taxi.dao_taxi.VehiculoDao
import com.app.taxi.dao_taxi.ViajesDao
import com.app.taxi.models.Administrador
import com.app.taxi.models.Chofer
import com.app.taxi.models.Cliente
import com.app.taxi.models.Ruta
import com.app.taxi.models.Solicitud_Taxi
import com.app.taxi.models.Vehiculo
import com.app.taxi.models.Viajes

@Database(
    entities = [
        Administrador::class,
        Chofer::class,
        Vehiculo::class,
        Cliente::class,
        Solicitud_Taxi::class,
        Ruta::class,
        Viajes::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DB_taxi: RoomDatabase() {

    abstract fun administradorDao(): AdministradorDao
    abstract fun choferDao(): ChoferDao
    abstract fun vehiculoDao(): VehiculoDao
    abstract fun clienteDao(): ClienteDao
    abstract fun solicitudTaxiDao(): SolicitudTaxiDao
    abstract fun rutaDao(): RutaDao
    abstract fun viajesDao(): ViajesDao

    companion object {
        @Volatile
        private var INSTANCE: DB_taxi? = null

        fun getDatabase(context: Context): DB_taxi {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DB_taxi::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
