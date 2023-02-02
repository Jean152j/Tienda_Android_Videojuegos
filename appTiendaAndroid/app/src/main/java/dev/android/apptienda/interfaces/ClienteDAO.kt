package dev.android.apptienda.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.android.apptienda.clases.Cliente

@Dao
interface ClienteDAO {

    @Query("SELECT * FROM cliente")
    fun obtenerClientes():LiveData<List<Cliente>>

    @Query("SELECT * FROM cliente WHERE idCliente = :id")
    fun obtenerCliente(id:Int):LiveData<Cliente>

    @Insert
    fun insertarCliente(vararg cliente: Cliente):List<Long>

    @Update
    fun actualizarCliente(cliente: Cliente)

    @Delete
    fun eliminarCliente(cliente: Cliente)
}