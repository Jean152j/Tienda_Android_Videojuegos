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
import kotlinx.android.synthetic.main.activity_detalle_vendedor.*
import kotlinx.android.synthetic.main.activity_nuevo_vendedor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleVendedorActivity : AppCompatActivity() {
    private lateinit var database:AppDatabase
    private lateinit var menu: dev.android.apptienda.clases.Vendedor
    private lateinit var menuLiveData: LiveData<dev.android.apptienda.clases.Vendedor>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_vendedor)
        cargarVendedor()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.acciones_menu, menu)
        return true
    }

    private fun cargarVendedor(){
        database = AppDatabase.getDatabase(this)
        val idVendedor = intent.getIntExtra("id", 0)

        menuLiveData = database.vendedores().obtenerVendedor(idVendedor)
        menuLiveData.observe(this, Observer {
            menu = it

            txtDetalleVendedorCedula.setText(menu.ced_vendedor)
            txtDetalleVendedorCodigo.setText(menu.codigo_vendedor)
            txtDetalleVendedorNombre.setText(menu.nom_vendedor)
            txtDetalleVendedorApellido.setText(menu.ape_vendedor)
            txtDetalleVendedorTelefono.setText(menu.tel_vendedor)

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.opEditarMenu -> {
                editarVendedor()
                true
            }

            R.id.opEliminarMenu -> {
                eliminarVendedor()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editarVendedor(){
        database = AppDatabase.getDatabase(this)

        val cedula = txtDetalleVendedorCedula.text.toString()
        val cod = txtDetalleVendedorCodigo.text.toString()
        val nombre = txtDetalleVendedorNombre.text.toString()
        val apellido = txtDetalleVendedorApellido.text.toString()
        val tel = txtDetalleVendedorTelefono.text.toString()

        menu.ced_vendedor = cedula
        menu.codigo_vendedor = cod
        menu.nom_vendedor = nombre
        menu.ape_vendedor = apellido
        menu.tel_vendedor = tel

        CoroutineScope(Dispatchers.IO).launch {
            database.vendedores().actualizarVendedor(menu)
            this@DetalleVendedorActivity.finish()
        }
    }

    private fun eliminarVendedor() {
        menuLiveData.removeObservers(this)
        CoroutineScope(Dispatchers.IO).launch {
            database.vendedores().eliminarVendedor(menu)
            this@DetalleVendedorActivity.finish()
        }
    }
}