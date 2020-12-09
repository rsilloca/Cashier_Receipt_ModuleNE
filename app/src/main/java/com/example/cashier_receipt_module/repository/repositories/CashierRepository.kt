package com.example.cashier_receipt_module.repository.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cashier_receipt_module.repository.dao.CashierDao
import com.example.cashier_receipt_module.repository.models.Cashier

class CashierRepository(private val cashierDao: CashierDao) {

    val allCashiers: LiveData<List<Cashier>> = cashierDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cashier: Cashier) {
        cashierDao.insert(cashier)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(cashier: Cashier) {
        cashierDao.update(cashier)
    }

}