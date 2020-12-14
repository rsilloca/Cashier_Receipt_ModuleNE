package com.example.cashier_receipt_module.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cashier_receipt_module.repository.dao.CashReceiptIncomeDao
import com.example.cashier_receipt_module.repository.dao.CashierDao
import com.example.cashier_receipt_module.repository.dao.ClientDao
import com.example.cashier_receipt_module.repository.models.CashReceiptIncome
import com.example.cashier_receipt_module.repository.models.Cashier
import com.example.cashier_receipt_module.repository.models.Client

@Database(
    entities = [
        Cashier::class,
        Client::class,
        CashReceiptIncome::class
    ],
    version = 1,
    exportSchema = false)
abstract class CashierReceiptModuleDatabase : RoomDatabase() {

    abstract fun cashierDao(): CashierDao
    abstract fun clientDao(): ClientDao
    abstract fun cashReceiptDao(): CashReceiptIncomeDao

    companion object {

        @Volatile
        private var INSTANCE: CashierReceiptModuleDatabase? = null

        fun getDatabase(
            context: Context
        ): CashierReceiptModuleDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CashierReceiptModuleDatabase::class.java,
                    "cr_module_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroy() {
            INSTANCE = null
        }
    }

}