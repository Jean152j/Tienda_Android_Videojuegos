package dev.android.apptienda.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.android.apptienda.clases.Cliente
import dev.android.apptienda.clases.Vendedor

@Dao
interface VendedorDAO {

    @Query("SELECT * FROM vendedor")
    fun obtenerVendedores():LiveData<List<Vendedor>>

    @Query("SELECT * FROM vendedor WHERE idVendedor = :id")
    fun obtenerVendedor(id:Int):LiveData<Vendedor>

    @Insert
    fun insertarVendedor(vararg vendedor: Vendedor):List<Long>

    @Update
    fun actualizarVendedor(vendedor: Vendedor)

    @Delete
    fun eliminarVendedor(vendedor: Vendedor)
}