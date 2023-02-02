package dev.android.apptienda.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.android.apptienda.clases.Cliente
import dev.android.apptienda.clases.Pedido

@Dao
interface PedidoDAO {

    @Query("SELECT * FROM pedido")
    fun obtenerPedidos():LiveData<List<Pedido>>

    @Query("SELECT * FROM pedido WHERE idPedido = :id")
    fun obtenerPedido(id:Int):LiveData<Pedido>

    @Insert
    fun insertarPedido(vararg pedido: Pedido):List<Long>

    @Update
    fun actualizarPedido(pedido: Pedido)

    @Delete
    fun eliminarPedido(pedido: Pedido)
}