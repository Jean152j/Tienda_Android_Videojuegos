package dev.android.apptienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import dev.android.apptienda.clases.AppTienda.Companion.preferencias
import kotlinx.android.synthetic.main.activity_opciones.*

class OpcionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)
        clickBotones()
        if (preferencias.devolverEstado()){
            textViewBienvenido.setText("Bienvenido "+preferencias.devolverUsuario())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_superior,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.opCerrarSesion ->{
                preferencias.guardarEstado(false)
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clickBotones(){
        btnProductos.setOnClickListener{
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
        }
        btnPedidos.setOnClickListener{
            val intent = Intent(this, PedidosActivity::class.java)
            startActivity(intent)
        }
        btnClientes.setOnClickListener{
            val intent = Intent(this, ClientesActivity::class.java)
            startActivity(intent)
        }
        btnVendedores.setOnClickListener{
            val intent = Intent(this, VendedoresActivity::class.java)
            startActivity(intent)
        }
        btnPago.setOnClickListener{
            val intent = Intent(this, PagoActivity::class.java)
            startActivity(intent)
        }
        btnNavegacion.setOnClickListener{
            val intent = Intent(this, BusquedasActivity::class.java)
            startActivity(intent)
        }

    }
}