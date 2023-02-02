package dev.android.apptienda.clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dev.android.apptienda.ControladorImagen
import dev.android.apptienda.R
import kotlinx.android.synthetic.main.fila_menu.view.*
import kotlinx.android.synthetic.main.fila_menu1.view.*

class VendedorAdapter(
    private val contexto: Context,
    private val listaVendedores:List<Vendedor>):ArrayAdapter<Vendedor>(contexto,0,listaVendedores) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(contexto).inflate(R.layout.fila_menu1,parent,false)
        val vendedor = listaVendedores[position]
        layout.txtFila1.setText("Cédula Vendedor: ${vendedor.ced_vendedor}")
        layout.txtFila2.setText("Nombre: ${vendedor.nom_vendedor}")
        layout.txtFila3.setText("Apellido: ${vendedor.ape_vendedor}")
        layout.txtFila4.setText("Código: ${vendedor.codigo_vendedor}")
        layout.txtFila5.setText("Teléfono: ${vendedor.tel_vendedor}")

        return layout
    }
}