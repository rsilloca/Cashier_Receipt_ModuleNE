package com.example.cashier_receipt_module.repository.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cashier_receipt_module.repository.models.Cashier

@Dao
interface CashierDao {
    @Insert
    fun insert(cashier: Cashier)

    @Update
    fun update(cashier: Cashier)

    @Query("SELECT * FROM cashiers WHERE Id = :key")
    fun getCashier(key: Long): LiveData<Cashier?>

    @Query("SELECT * FROM cashiers")
    fun getAll(): LiveData<List<Cashier>>

    @Query("DELETE FROM cashiers")
    fun clear()

    @Query("SELECT * FROM cashiers WHERE Nombre LIKE :query")
    fun searchCashiers(query: String): List<Cashier>
}