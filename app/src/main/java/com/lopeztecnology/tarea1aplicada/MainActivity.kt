package com.lopeztecnology.tarea1aplicada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lopeztecnology.tarea1aplicada.ui.Pantallas.PantallaRegistro
import com.lopeztecnology.tarea1aplicada.ui.theme.Tarea1aplicadaTheme

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(this)

        setContent {
            Tarea1aplicadaTheme {
                PantallaRegistro(db)
            }
        }
    }
}
