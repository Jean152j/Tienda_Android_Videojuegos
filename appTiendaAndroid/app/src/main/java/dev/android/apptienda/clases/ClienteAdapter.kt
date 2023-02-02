package dev.android.apptienda.clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dev.android.apptienda.R
import kotlinx.android.synthetic.main.fila_menu.view.*
import kotlinx.android.synthetic.main.fila_menu1.view.*

class ClienteAdapter(
    private val contexto: Context,
    private val listaClientes:List<Cliente>):ArrayAdapter<Cliente>(contexto,0,listaClientes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(contexto).inflate(R.layout.fila_menu1,parent,false)
        val cliente = listaClientes[position]
        layout.txtFila1.setText("Cédula Cliente: ${cliente.ced_cliente}")
        layout.txtFila2.setText("Nombre: ${cliente.nom_cliente}")
        layout.txtFila3.setText("Apellido: ${cliente.ape_cliente}")
        layout.txtFila4.setText("Dirección: ${cliente.dir_cliente}")
        layout.txtFila5.setText("Teléfono: ${cliente.tel_cliente}")
        return layout
    }
}