package dev.android.apptienda.clases

import android.content.Context

class Preferencias(val context:Context) {
    val SHARED_APP = "MyApp"
    val SHARED_ESTADO = "estado"
    val SHARED_USUARIO="usuario"

    val almacenamiento = context.getSharedPreferences(SHARED_APP,Context.MODE_PRIVATE)

    fun guardarEstado(estado:Boolean){
        almacenamiento.edit().putBoolean(SHARED_ESTADO,estado).apply()
    }

    fun devolverEstado():Boolean{
        return almacenamiento.getBoolean(SHARED_ESTADO,false)
    }

    fun guardarUsuario(usuario:String){
        almacenamiento.edit().putString(SHARED_USUARIO,usuario).apply()
    }

    fun devolverUsuario():String?{
        return almacenamiento.getString(SHARED_USUARIO,"")
    }

}