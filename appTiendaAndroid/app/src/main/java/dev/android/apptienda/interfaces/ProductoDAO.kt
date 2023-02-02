package dev.android.apptienda.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.android.apptienda.clases.Cliente
import dev.android.apptienda.clases.Producto

@Dao
interface ProductoDAO {

    @Query("SELECT * FROM producto")
    fun obtenerProductos():LiveData<List<Producto>>

    @Query("SELECT * FROM producto WHERE idProducto = :id")
    fun obtenerProducto(id:Int):LiveData<Producto>

    @Insert
    fun insertarProducto(vararg producto: Producto):List<Long>

    @Update
    fun actualizarProducto(producto: Producto)

    @Delete
    fun eliminarProducto(producto: Producto)
}