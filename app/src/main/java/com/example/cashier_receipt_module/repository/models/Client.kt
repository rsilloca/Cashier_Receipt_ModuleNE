package com.example.cashier_receipt_module.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client (
    @PrimaryKey(autoGenerate = true)
    val Id: Long,
    val Codigo: String = "000000",
    val Nombre: String = "",
    val EstReg: String = "A"
)