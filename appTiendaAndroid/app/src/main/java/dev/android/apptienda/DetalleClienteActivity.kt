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
import kotlinx.android.synthetic.main.activity_detalle_cliente.*
import kotlinx.android.synthetic.main.activity_nuevo_cliente.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleClienteActivity : AppCompatActivity() {
    private lateinit var database:AppDatabase
    private lateinit var menu: dev.android.apptienda.clases.Cliente
    private lateinit var menuLiveData: LiveData<dev.android.apptienda.clases.Cliente>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_cliente)
        cargarCliente()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.acciones_menu, menu)
        return true
    }

    private fun cargarCliente(){
        database = AppDatabase.getDatabase(this)
        val idCliente = intent.getIntExtra("id", 0)

        menuLiveData = database.clientes().obtenerCliente(idCliente)
        menuLiveData.observe(this, Observer {
            menu = it

            txtDetalleClienteCedula.setText(menu.ced_cliente)
            txtDetalleClienteNombre.setText(menu.nom_cliente)
            txtDetalleClienteApellido.setText(menu.ape_cliente)
            txtDetalleClienteDireccion.setText(menu.dir_cliente)
            txtDetalleClienteTelefono.setText(menu.tel_cliente)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.opEditarMenu -> {
                editarCliente()
                true
            }

            R.id.opEliminarMenu -> {
                eliminarCliente()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editarCliente(){
        database = AppDatabase.getDatabase(this)

        val cedula = txtDetalleClienteCedula.text.toString()
        val nombre = txtDetalleClienteNombre.text.toString()
        val apellido = txtDetalleClienteApellido.text.toString()
        val dir = txtDetalleClienteDireccion.text.toString()
        val tel = txtDetalleClienteTelefono.text.toString()

        menu.ced_cliente = cedula
        menu.nom_cliente = nombre
        menu.ape_cliente = apellido
        menu.dir_cliente = dir
        menu.tel_cliente = tel

        CoroutineScope(Dispatchers.IO).launch {
            database.clientes().actualizarCliente(menu)
            this@DetalleClienteActivity.finish()
        }
    }

    private fun eliminarCliente() {
        menuLiveData.removeObservers(this)
        CoroutineScope(Dispatchers.IO).launch {
            database.clientes().eliminarCliente(menu)
            this@DetalleClienteActivity.finish()
        }
    }
}