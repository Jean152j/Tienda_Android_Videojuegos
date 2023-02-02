package dev.android.apptienda.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pedido")

class Pedido(
    var ced_pedido:String,
    var nom_pedido:String,
    var cant_productos:Int,
    var descripcion:String,
    @PrimaryKey(autoGenerate = true)
    var idPedido:Int =0):Serializable
