package com.example.cashier_receipt_module.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cashiers")
data class Cashier (
    @PrimaryKey(autoGenerate = true)
    val Id: Long,
    var Codigo: String = "000000",
    var Nombre: String = "Default",
    var EstReg: String = "A"
)