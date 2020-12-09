package com.example.cashier_receipt_module.repository.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cash_receipts_income",
    foreignKeys = [
        ForeignKey(
            entity = Cashier::class,
            parentColumns = arrayOf("Id"),
            childColumns = arrayOf("IdCashier"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Client::class,
            parentColumns = arrayOf("Id"),
            childColumns = arrayOf("IdClient"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CashReceiptIncome(
    @PrimaryKey(autoGenerate = true)
    val Id: Long,
    val IdCashier: Long,
    val IdClient: Long,
    val Numero: Int = 0,
    val Monto: Double = 0.0,
    val EstReg: String = "A"
)