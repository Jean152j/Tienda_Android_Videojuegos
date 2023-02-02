package dev.android.apptienda

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dev.android.apptienda.clases.Cliente
import dev.android.apptienda.clases.ClienteAdapter
import dev.android.apptienda.clases.Pedido
import dev.android.apptienda.clases.PedidoAdapter
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.activity_productos.*
import kotlinx.android.synthetic.main.activity_productos.lstMenu

data class ClientesTienda(var nombre:String, var detalle:String)

class ClientesActivity : AppCompatActivity() {

    val clientesTienda = arrayListOf(
        ClientesTienda("José Andrade", "1805002123"),
        ClientesTienda("Carlos Pérez", "1805002120"),
        ClientesTienda("Julio López", "1805002345"),
    )

    private lateinit var database: AppDatabase
    private lateinit var cliente: Cliente
    private lateinit var menuLiveData: LiveData<Cliente>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)
        //crearLista()
        click()
        cargarListaClientes()
    }

    private fun crearLista(){

        val adaptador = Adaptador(applicationContext, clientesTienda)
        lstMenu.adapter = adaptador
    }

    private class Adaptador(context: Context, datos:ArrayList<ClientesTienda>): BaseAdapter()
    {
        val datosCliente = datos
        val ctx = context

        private inner class ViewHolder(){
            internal var nombre: TextView?= null
            internal var detalle: TextView?= null
            internal var flecha: ImageView?= null
        }

        override fun getCount(): Int {
            return datosCliente.size
        }

        override fun getItem(position: Int): Any {
            return datosCliente[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, row: View?, parent: ViewGroup?): View {
            var viewHolder:ViewHolder
            var rowView = row

            if(rowView == null){
                viewHolder = ViewHolder()
                val inflater = LayoutInflater.from(ctx)
                rowView = inflater.inflate(R.layout.fila_personalizada, parent, false)

                viewHolder.nombre = rowView.findViewById(R.id.txtNombre) as TextView
                viewHolder.detalle = rowView.findViewById(R.id.txtDetalle) as TextView
                viewHolder.flecha = rowView.findViewById(R.id.imgFlecha) as ImageView
                rowView.tag = viewHolder
            }else{
                viewHolder = rowView.tag as ViewHolder
            }

            viewHolder.nombre!!.setText(datosCliente.get(position).nombre)
            viewHolder.detalle!!.setText(datosCliente.get(position).detalle)
            viewHolder.flecha!!.setImageResource(R.drawable.ic_flecha_derecha)

            return rowView!!
        }

    }

    private fun click(){
        btnNuevoCliente.setOnClickListener{
            val intent = Intent(applicationContext, NuevoClienteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarListaClientes(){
        var listaClientes = emptyList<Cliente>()
        val database = AppDatabase.getDatabase(this)

        database.clientes().obtenerClientes().observe(this, Observer {
            listaClientes = it
            val adaptador = ClienteAdapter(this, listaClientes)
            lstMenu.adapter = adaptador
        })

        lstMenu.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetalleClienteActivity::class.java)
            intent.putExtra("id", listaClientes[position].idCliente)
            startActivity(intent)
        }
    }
}