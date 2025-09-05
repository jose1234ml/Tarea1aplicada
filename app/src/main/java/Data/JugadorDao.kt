package com.lopeztecnology.tarea1aplicada

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JugadorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarJugador(jugador: Jugador)

    @Query("SELECT * FROM jugadores WHERE nombres = :nombre LIMIT 1")
    suspend fun buscarPorNombre(nombre: String): Jugador?

    @Query("SELECT * FROM jugadores")
    suspend fun obtenerTodos(): List<Jugador>
}
