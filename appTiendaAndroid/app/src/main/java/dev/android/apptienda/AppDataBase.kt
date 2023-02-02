package dev.android.apptienda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.android.apptienda.clases.Cliente
import dev.android.apptienda.clases.Pedido
import dev.android.apptienda.clases.Producto
import dev.android.apptienda.clases.Vendedor
import dev.android.apptienda.interfaces.ClienteDAO
import dev.android.apptienda.interfaces.PedidoDAO
import dev.android.apptienda.interfaces.ProductoDAO
import dev.android.apptienda.interfaces.VendedorDAO

@Database(entities = [Producto::class, Pedido::class, Cliente::class, Vendedor::class] , version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productos():ProductoDAO
    abstract fun pedidos():PedidoDAO
    abstract fun clientes():ClienteDAO
    abstract fun vendedores():VendedorDAO

    companion object{

        @Volatile
        private var INSTANCIA:AppDatabase?= null

        fun getDatabase(contexto:Context):AppDatabase{
            val tempInstancia = INSTANCIA

            if (tempInstancia != null) {
                return tempInstancia
            }

            synchronized(this){
                val instancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    AppDatabase::class.java,
                    "app_tienda"
                ).build()

                INSTANCIA = instancia

                return instancia
            }
        }

    }

}