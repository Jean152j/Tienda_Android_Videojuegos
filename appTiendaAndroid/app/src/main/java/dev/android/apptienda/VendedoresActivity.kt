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
import dev.android.apptienda.clases.Vendedor
import dev.android.apptienda.clases.VendedorAdapter
import kotlinx.android.synthetic.main.activity_productos.*
import kotlinx.android.synthetic.main.activity_productos.lstMenu
import kotlinx.android.synthetic.main.activity_vendedores.*

data class VendedoresTienda(var nombre:String, var detalle:String)

class VendedoresActivity : AppCompatActivity() {

    val venderoresTienda = arrayListOf(
        VendedoresTienda("Julián Galarza", "1806002123"),
        VendedoresTienda("Andrea Heredia", "1803002120"),
        VendedoresTienda("Kevin Sánchez", "1801002345"),
    )

    private lateinit var database: AppDatabase
    private lateinit var vendedor: Vendedor
    private lateinit var menuLiveData: LiveData<Vendedor>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedores)
        //crearLista()
        click()
        cargarListaVendedores()
    }

    private fun crearLista(){

        val adaptador = Adaptador(applicationContext, venderoresTienda)
        lstMenu.adapter = adaptador
    }

    private class Adaptador(context: Context, datos:ArrayList<VendedoresTienda>): BaseAdapter()
    {
        val datosVendedor = datos
        val ctx = context

        private inner class ViewHolder(){
            internal var nombre: TextView?= null
            internal var detalle: TextView?= null
            internal var flecha: ImageView?= null
        }

        override fun getCount(): Int {
            return datosVendedor.size
        }

        override fun getItem(position: Int): Any {
            return datosVendedor[position]
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

            viewHolder.nombre!!.setText(datosVendedor.get(position).nombre)
            viewHolder.detalle!!.setText(datosVendedor.get(position).detalle)
            viewHolder.flecha!!.setImageResource(R.drawable.ic_flecha_derecha)

            return rowView!!
        }

    }

    private fun click(){
        btnNuevoVendedor.setOnClickListener{
            val intent = Intent(applicationContext, NuevoVendedorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarListaVendedores(){
        var listaVendedores = emptyList<Vendedor>()
        val database = AppDatabase.getDatabase(this)

        database.vendedores().obtenerVendedores().observe(this, Observer {
            listaVendedores = it
            val adaptador = VendedorAdapter(this, listaVendedores)
            lstMenu.adapter = adaptador
        })

        lstMenu.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetalleVendedorActivity::class.java)
            intent.putExtra("id", listaVendedores[position].idVendedor)
            startActivity(intent)
        }
    }
}