package com.example.cashier_receipt_module.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client (
    @PrimaryKey(autoGenerate = true)
    val Id: Long,
    var Codigo: String = "000000",
    var Nombre: String = "",
    var EstReg: String = "A"
)