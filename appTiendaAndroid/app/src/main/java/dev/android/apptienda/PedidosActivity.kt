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
import dev.android.apptienda.clases.Pedido
import dev.android.apptienda.clases.PedidoAdapter
import dev.android.apptienda.clases.Producto
import dev.android.apptienda.clases.ProductoAdapter
import kotlinx.android.synthetic.main.activity_pedidos.*
import kotlinx.android.synthetic.main.activity_productos.*
import kotlinx.android.synthetic.main.activity_productos.lstMenu

data class PedidosTienda(var nombre:String, var detalle:String)

class PedidosActivity : AppCompatActivity() {

    val pedidosTienda = arrayListOf(
        PedidosTienda("P01", "Se solicitan 10 juegos de PS4"),
        PedidosTienda("P02", "Se solicitan 2 juegos de PS5"),
        PedidosTienda("P03", "Se solicitan 3 juegos de PS4"),
    )

    private lateinit var database: AppDatabase
    private lateinit var pedido: Pedido
    private lateinit var menuLiveData: LiveData<Pedido>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)
       //crearLista()
        click()
        cargarListaPedidos()
    }

    private fun crearLista(){

        val adaptador = Adaptador(applicationContext, pedidosTienda)
        lstMenu.adapter = adaptador
    }

    private class Adaptador(context: Context, datos: ArrayList<PedidosTienda>): BaseAdapter()
    {
        val datosPedido = datos
        val ctx = context

        private inner class ViewHolder(){
            internal var nombre: TextView?= null
            internal var detalle: TextView?= null
            internal var flecha: ImageView?= null
        }

        override fun getCount(): Int {
            return datosPedido.size
        }

        override fun getItem(position: Int): Any {
            return datosPedido[position]
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

            viewHolder.nombre!!.setText(datosPedido.get(position).nombre)
            viewHolder.detalle!!.setText(datosPedido.get(position).detalle)
            viewHolder.flecha!!.setImageResource(R.drawable.ic_flecha_derecha)

            return rowView!!
        }

    }

    private fun click(){
        btnNuevoPedido.setOnClickListener{
            val intent = Intent(applicationContext, NuevoPedidoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarListaPedidos(){
        var listaPedidos = emptyList<Pedido>()
        val database = AppDatabase.getDatabase(this)

        database.pedidos().obtenerPedidos().observe(this, Observer {
            listaPedidos = it
            val adaptador = PedidoAdapter(this, listaPedidos)
            lstMenu.adapter = adaptador
        })

        lstMenu.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetallePedidoActivity::class.java)
            intent.putExtra("id", listaPedidos[position].idPedido)
            startActivity(intent)
        }
    }
}