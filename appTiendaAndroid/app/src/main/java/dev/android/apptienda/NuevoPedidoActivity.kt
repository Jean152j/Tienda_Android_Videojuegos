package dev.android.apptienda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_nuevo_pedido.*
import kotlinx.android.synthetic.main.activity_nuevo_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoPedidoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_pedido)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.accion_guardar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.opGuardarMenu -> {
                registrarPedido()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registrarPedido(){
        val database = AppDatabase.getDatabase(this)
        val ced_pedido = txtPedidoCedula.text.toString()
        val nom_pedido = txtPedidoNombre.text.toString()
        val cant_productos = txtPedidoCantidad.text.toString()
        val descripcion = txtPedidoDescripcion.text.toString()

        val pedido = dev.android.apptienda.clases.Pedido(ced_pedido, nom_pedido, cant_productos.toInt(), descripcion)
        CoroutineScope(Dispatchers.IO).launch{
            database.pedidos().insertarPedido(pedido)[0]
            this@NuevoPedidoActivity.finish()
        }
    }
}