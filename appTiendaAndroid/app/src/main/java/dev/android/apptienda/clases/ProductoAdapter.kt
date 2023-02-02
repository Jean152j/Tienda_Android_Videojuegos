package dev.android.apptienda.clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dev.android.apptienda.ControladorImagen
import dev.android.apptienda.R
import kotlinx.android.synthetic.main.fila_menu.view.*

class ProductoAdapter(
    private val contexto: Context,
    private val listaProductos:List<Producto>):ArrayAdapter<Producto>(contexto,0,listaProductos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(contexto).inflate(R.layout.fila_menu,parent,false)
        val producto = listaProductos[position]
        layout.txtFilaMenu.setText(producto.nombre)
        layout.txtFilaDescripcion.setText(producto.detalle)
        layout.txtFilaPrecio.setText("$${producto.precio}")
        val uriImagen = ControladorImagen.obtenerUriImagen(contexto,producto.idProducto.toLong())
        layout.imgFilaMenu.setImageURI(uriImagen)

        return layout
    }
}