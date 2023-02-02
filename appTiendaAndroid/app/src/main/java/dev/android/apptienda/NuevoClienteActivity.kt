package dev.android.apptienda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_nuevo_cliente.*
import kotlinx.android.synthetic.main.activity_nuevo_pedido.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoClienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_cliente)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.accion_guardar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.opGuardarMenu -> {
                registrarCliente()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registrarCliente(){
        val database = AppDatabase.getDatabase(this)
        val ced_cliente = txtClienteCedula.text.toString()
        val nom_cliente = txtClienteNombre.text.toString()
        val ape_cliente = txtClienteApellido.text.toString()
        val dir_cliente = txtClienteDireccion.text.toString()
        val tel_cliente = txtClienteTelefono.text.toString()

        val cliente = dev.android.apptienda.clases.Cliente(ced_cliente, nom_cliente, ape_cliente, dir_cliente, tel_cliente)
        CoroutineScope(Dispatchers.IO).launch{
            database.clientes().insertarCliente(cliente)[0]
            this@NuevoClienteActivity.finish()
        }
    }
}