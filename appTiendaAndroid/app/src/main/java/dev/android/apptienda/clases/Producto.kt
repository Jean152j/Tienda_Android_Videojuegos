package dev.android.apptienda.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "producto")

class Producto(
    var nombre:String,
    var detalle:String,
    var precio:Double,
    val imagen:Int,
    @PrimaryKey(autoGenerate = true)
    var idProducto:Int =0):Serializable
