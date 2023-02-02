package dev.android.apptienda.clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dev.android.apptienda.ControladorImagen
import dev.android.apptienda.R
import kotlinx.android.synthetic.main.fila_menu.view.*
import kotlinx.android.synthetic.main.fila_menu.view.txtFilaPrecio
import kotlinx.android.synthetic.main.fila_menu1.view.*

class PedidoAdapter(
    private val contexto: Context,
    private val listaPedidos:List<Pedido>):ArrayAdapter<Pedido>(contexto,0,listaPedidos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(contexto).inflate(R.layout.fila_menu1,parent,false)
        val pedido = listaPedidos[position]
        layout.txtFila1.setText("Nombre Propietario: ${pedido.nom_pedido}")
        layout.txtFila2.setText("Cédula: ${pedido.ced_pedido}")
        layout.txtFila3.setText("Cantidad De Productos: ${pedido.cant_productos}")
        layout.txtFila4.setText("Descripción: ${pedido.descripcion}")

        return layout
    }
}