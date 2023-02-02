package dev.android.apptienda.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "vendedor")

class Vendedor(
    var ced_vendedor:String,
    var codigo_vendedor:String,
    var nom_vendedor:String,
    var ape_vendedor:String,
    var tel_vendedor:String,
    @PrimaryKey(autoGenerate = true)
    var idVendedor:Int =0):Serializable
