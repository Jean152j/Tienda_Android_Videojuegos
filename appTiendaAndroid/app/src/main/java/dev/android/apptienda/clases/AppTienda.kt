package dev.android.apptienda.clases

import android.app.Application

class AppTienda:Application() {
    companion object{
        lateinit var preferencias:Preferencias
    }

    override fun onCreate() {
        super.onCreate()
        preferencias = Preferencias(applicationContext)
    }
}