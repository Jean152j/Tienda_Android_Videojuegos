package dev.android.apptienda.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cliente")

class Cliente(
    var ced_cliente:String,
    var nom_cliente:String,
    var ape_cliente:String,
    var dir_cliente:String,
    var tel_cliente:String,
    @PrimaryKey(autoGenerate = true)
    var idCliente:Int =0):Serializable
