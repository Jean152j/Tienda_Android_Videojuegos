package dev.android.apptienda

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_detalle_producto.*
import kotlinx.android.synthetic.main.activity_nuevo_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleProductoActivity : AppCompatActivity() {
    private lateinit var database:AppDatabase
    private lateinit var menu: dev.android.apptienda.clases.Producto
    private lateinit var menuLiveData: LiveData<dev.android.apptienda.clases.Producto>
    private val EDITAR = 2
    private val SELECCIONAR = 2
    private var uriImagen: Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)
        cargarProducto()
        cargar_imagen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == SELECCIONAR && resultCode == Activity.RESULT_OK ->{
                uriImagen = data!!.data
                imgDetalleProducto.setImageURI(uriImagen)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.acciones_menu, menu)
        return true
    }

    private fun cargar_imagen(){
        imgDetalleProducto.setOnClickListener(){
            ControladorImagen.seleccionarFotoGaleria(this, SELECCIONAR)
        }
    }

    private fun cargarProducto(){
        database = AppDatabase.getDatabase(this)
        val idProducto = intent.getIntExtra("id", 0)

        menuLiveData = database.productos().obtenerProducto(idProducto)
        menuLiveData.observe(this, Observer {
            menu = it

            txtDetalleProductoNombre.setText(menu.nombre)
            txtDetalleProductoDetalle.setText(menu.detalle)
            txtDetalleProductoPrecio.setText(menu.precio.toString())

            val uriImagen = ControladorImagen.obtenerUriImagen(this,idProducto.toLong())
            imgDetalleProducto.setImageURI(uriImagen)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.opEditarMenu -> {
                editarProducto()
                true
            }

            R.id.opEliminarMenu -> {
                eliminarProducto()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editarProducto(){
        database = AppDatabase.getDatabase(this)

        val nombre = txtDetalleProductoNombre.text.toString()
        val detalle = txtDetalleProductoDetalle.text.toString()
        val precio = txtDetalleProductoPrecio.text.toString()

        menu.nombre = nombre
        menu.detalle = detalle
        menu.precio = precio.toDouble()

        var idProducto = menu.idProducto

        CoroutineScope(Dispatchers.IO).launch {
            database.productos().actualizarProducto(menu)

            uriImagen?.let {
                ControladorImagen.guardarImagen(this@DetalleProductoActivity,idProducto.toLong(),it)
            }
            this@DetalleProductoActivity.finish()
        }
    }

    private fun eliminarProducto() {
        menuLiveData.removeObservers(this)
        CoroutineScope(Dispatchers.IO).launch {
            database.productos().eliminarProducto(menu)
            this@DetalleProductoActivity.finish()
        }
    }
}