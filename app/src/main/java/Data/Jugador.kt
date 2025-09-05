package com.lopeztecnology.tarea1aplicada

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class Jugador(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombres: String,
    val partidas: Int
)
