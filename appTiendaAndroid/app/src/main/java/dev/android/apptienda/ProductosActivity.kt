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
import dev.android.apptienda.clases.Producto
import dev.android.apptienda.clases.ProductoAdapter
import kotlinx.android.synthetic.main.activity_productos.*

data class ProductosTienda(var nombre:String, var detalle:String)

class ProductosActivity : AppCompatActivity() {

    val productosTienda = arrayListOf(
        ProductosTienda("PlayStation 5", "Consola de Última Generación"),
        ProductosTienda("GTA V", "Juego de Mundo Abierto"),
        ProductosTienda("The Last Of Us", "Juego de Suspenso"),
    )

    private lateinit var database: AppDatabase
    private lateinit var producto:Producto
    private lateinit var menuLiveData: LiveData<Producto>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        //crearLista()
        click()
        cargarListaProductos()
    }

    private fun crearLista(){

        val adaptador = Adaptador(applicationContext, productosTienda)
        lstMenu.adapter = adaptador
    }

    private class Adaptador(context: Context, datos:ArrayList<ProductosTienda>): BaseAdapter()
    {
        val datosProducto = datos
        val ctx = context

        private inner class ViewHolder(){
            internal var nombre: TextView?= null
            internal var detalle: TextView?= null
            internal var flecha: ImageView?= null
        }

        override fun getCount(): Int {
            return datosProducto.size
        }

        override fun getItem(position: Int): Any {
            return datosProducto[position]
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

            viewHolder.nombre!!.setText(datosProducto.get(position).nombre)
            viewHolder.detalle!!.setText(datosProducto.get(position).detalle)
            viewHolder.flecha!!.setImageResource(R.drawable.ic_flecha_derecha)

            return rowView!!
        }

    }

    private fun click(){
        btnNuevoProducto.setOnClickListener{
            val intent = Intent(applicationContext, NuevoProductoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarListaProductos(){
        var listaProductos = emptyList<Producto>()
        val database = AppDatabase.getDatabase(this)

        database.productos().obtenerProductos().observe(this, Observer {
            listaProductos = it
            val adaptador = ProductoAdapter(this, listaProductos)
            lstMenu.adapter = adaptador
        })

        lstMenu.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("id", listaProductos[position].idProducto)
            startActivity(intent)
        }

    }

}