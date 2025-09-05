package com.lopeztecnology.tarea1aplicada.ui.Pantallas

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.lopeztecnology.tarea1aplicada.AppDatabase
import com.lopeztecnology.tarea1aplicada.Jugador
import kotlinx.coroutines.launch

@Composable
fun PantallaRegistro(db: AppDatabase) {
    var nombre by remember { mutableStateOf("") }
    var partidas by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = context as ComponentActivity
    var listaJugadores by remember { mutableStateOf(listOf<Jugador>()) }

    // Cargar lista de jugadores al iniciar
    LaunchedEffect(Unit) {
        listaJugadores = db.jugadorDao().obtenerTodos()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = partidas,
            onValueChange = { partidas = it },
            label = { Text("Partidas") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (nombre.isBlank() || partidas.isBlank()) {
                    Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                } else {
                    activity.lifecycleScope.launch {
                        val existe = db.jugadorDao().buscarPorNombre(nombre)
                        if (existe != null) {
                            Toast.makeText(context, "Ya existe un jugador con ese nombre", Toast.LENGTH_SHORT).show()
                        } else {
                            val jugador = Jugador(nombres = nombre, partidas = partidas.toInt())
                            db.jugadorDao().insertarJugador(jugador)
                            Toast.makeText(context, "Jugador guardado correctamente", Toast.LENGTH_SHORT).show()
                            nombre = ""
                            partidas = ""
                            listaJugadores = db.jugadorDao().obtenerTodos()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Jugadores guardados:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(listaJugadores) { jugador ->
                Text("${jugador.nombres} - Partidas: ${jugador.partidas}", modifier = Modifier.padding(4.dp))
            }
        }
    }
}
