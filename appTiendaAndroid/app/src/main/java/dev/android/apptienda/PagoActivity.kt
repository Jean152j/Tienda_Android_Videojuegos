package dev.android.apptienda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.android.apptienda.databinding.ActivityPagoBinding

class PagoActivity : AppCompatActivity(), FragmentoCosto.ComunicadorFragmentos {

    lateinit var binding: ActivityPagoBinding
    var numeroFragmento = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)

        binding = ActivityPagoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUno.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("numero", ++numeroFragmento)

            val transaccion = supportFragmentManager.beginTransaction()
            val fragmento = FragmentoProducto()

            fragmento.arguments = bundle
            transaccion.replace(R.id.contenedor, fragmento)
            transaccion.addToBackStack(null)
            transaccion.commit()


        }

        binding.btnDos.setOnClickListener {
            val transaccion = supportFragmentManager.beginTransaction()
            val fragmento = FragmentoCosto()

            transaccion.replace(R.id.contenedor, fragmento)
            transaccion.addToBackStack(null)
            transaccion.commit()
        }

    }

    override fun enviarDatos(datos: String) {
        binding.txtMain.setText("Precio Total  : $$datos" )

    }

}