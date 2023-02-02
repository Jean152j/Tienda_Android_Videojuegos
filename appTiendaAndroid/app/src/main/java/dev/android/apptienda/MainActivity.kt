package dev.android.apptienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.android.apptienda.clases.AppTienda.Companion.preferencias
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLoginClick()
        if (revisarEstado()){
            startActivity(Intent(this, OpcionesActivity::class.java))
        }
    }

    private fun btnLoginClick(){
        btnLogin.setOnClickListener {
            if(edtUsername.text.toString().equals("") || edtPassword.text.toString().equals("")){
                Toast.makeText(this,"Debe ingresar todos los campos", Toast.LENGTH_LONG).show()
            }else{
                preferencias.guardarEstado(btnRecuerdame.isChecked)
                preferencias.guardarUsuario(edtUsername.text.toString())
                val intent = Intent(this,OpcionesActivity::class.java);
                startActivity(intent);
            }

        }
    }

    private fun revisarEstado():Boolean{
        return preferencias.devolverEstado()
    }

}