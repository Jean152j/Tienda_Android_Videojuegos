package dev.android.apptienda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_detalle_pedido.*
import kotlinx.android.synthetic.main.activity_nuevo_pedido.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallePedidoActivity : AppCompatActivity() {
    private lateinit var database:AppDatabase
    private lateinit var menu: dev.android.apptienda.clases.Pedido
    private lateinit var menuLiveData: LiveData<dev.android.apptienda.clases.Pedido>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)
        cargarPedido()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.acciones_menu, menu)
        return true
    }

    private fun cargarPedido(){
        database = AppDatabase.getDatabase(this)
        val idPedido = intent.getIntExtra("id", 0)

        menuLiveData = database.pedidos().obtenerPedido(idPedido)
        menuLiveData.observe(this, Observer {
            menu = it

            txtDetallePedidoCedula.setText(menu.ced_pedido)
            txtDetallePedidoNombre.setText(menu.nom_pedido)
            txtDetallePedidoCantidad.setText(menu.cant_productos.toString())
            txtDetallePedidoDescripcion.setText(menu.descripcion)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.opEditarMenu -> {
                editarPedido()
                true
            }

            R.id.opEliminarMenu -> {
                eliminarPedido()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editarPedido(){
        database = AppDatabase.getDatabase(this)

        val cedula = txtDetallePedidoCedula.text.toString()
        val nombre = txtDetallePedidoNombre.text.toString()
        val cant_pro = txtDetallePedidoCantidad.text.toString()
        val desc = txtDetallePedidoDescripcion.text.toString()

        menu.ced_pedido = cedula
        menu.nom_pedido = nombre
        menu.cant_productos = cant_pro.toInt()
        menu.descripcion = desc

        CoroutineScope(Dispatchers.IO).launch {
            database.pedidos().actualizarPedido(menu)
            this@DetallePedidoActivity.finish()
        }
    }

    private fun eliminarPedido() {
        menuLiveData.removeObservers(this)
        CoroutineScope(Dispatchers.IO).launch {
            database.pedidos().eliminarPedido(menu)
            this@DetallePedidoActivity.finish()
        }
    }
}