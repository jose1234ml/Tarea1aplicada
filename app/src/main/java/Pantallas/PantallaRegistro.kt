package com.lopeztecnology.tarea1aplicada.ui.Pantallas

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    LaunchedEffect(Unit) {
        listaJugadores = db.jugadorDao().obtenerTodos()
    }

    // Fondo degradado azul
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF0D47A1), Color(0xFF1976D2))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Registro de Jugadores",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFBBDEFB),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campos de texto
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = partidas,
            onValueChange = { partidas = it },
            label = { Text("Partidas", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Botón azul claro
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
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF64B5F6),
                contentColor = Color.White
            )
        ) {
            Text(text = "Guardar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Jugadores guardados:",
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listaJugadores) { jugador ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1976D2)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "${jugador.nombres} - Partidas: ${jugador.partidas}",
                        color = Color.White,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}
