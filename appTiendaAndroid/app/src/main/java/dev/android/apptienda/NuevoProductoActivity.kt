package dev.android.apptienda

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_nuevo_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoProductoActivity : AppCompatActivity() {

    private val SELECCIONAR = 1
    private var uriImagen: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)
        cargar_imagen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode == SELECCIONAR && resultCode == Activity.RESULT_OK -> {
                uriImagen = data!!.data
                imgProductoNuevo.setImageURI(uriImagen)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.accion_guardar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.opGuardarMenu -> {
                registrarProducto()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun cargar_imagen(){
        imgProductoNuevo.setOnClickListener{
            ControladorImagen.seleccionarFotoGaleria(this, SELECCIONAR)
        }
    }

    private fun registrarProducto(){
        val database = AppDatabase.getDatabase(this)
        val nombre = txtProductoNombre.text.toString()
        val detalle = txtProductoDetalle.text.toString()
        val precio = txtProductoPrecio.text.toString()

        val producto = dev.android.apptienda.clases.Producto(nombre, detalle, precio.toDouble(), R.drawable.placeholder)
        CoroutineScope(Dispatchers.IO).launch{
            val id = database.productos().insertarProducto(producto)[0]
            uriImagen?.let {
                ControladorImagen.guardarImagen(this@NuevoProductoActivity, id, it)
            }
            this@NuevoProductoActivity.finish()
        }
    }

}