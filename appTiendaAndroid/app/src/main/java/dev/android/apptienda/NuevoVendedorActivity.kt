package dev.android.apptienda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_nuevo_cliente.*
import kotlinx.android.synthetic.main.activity_nuevo_vendedor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoVendedorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_vendedor)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.accion_guardar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.opGuardarMenu -> {
                registrarVendedor()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registrarVendedor(){
        val database = AppDatabase.getDatabase(this)
        val ced_vendedor = txtVendedorCedula.text.toString()
        val codigo_vendedor = txtVendedorCodigo.text.toString()
        val nom_vendedor = txtVendedorNombre.text.toString()
        val ape_vendedor = txtVendedorApellido.text.toString()
        val tel_vendedor= txtVendedorTelefono.text.toString()

        val vendedor = dev.android.apptienda.clases.Vendedor( ced_vendedor,  codigo_vendedor,  nom_vendedor,  ape_vendedor,  tel_vendedor)
        CoroutineScope(Dispatchers.IO).launch{
            database.vendedores().insertarVendedor(vendedor)[0]
            this@NuevoVendedorActivity.finish()
        }
    }
}