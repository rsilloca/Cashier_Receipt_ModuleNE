package com.example.cashier_receipt_module.repository.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cashier_receipt_module.repository.dao.CashierDao
import com.example.cashier_receipt_module.repository.models.Cashier

class CashierRepository(private val cashierDao: CashierDao) {

    val allCashiers: LiveData<List<Cashier>> = cashierDao.getAll()

    @WorkerThread
    suspend fun insert(cashier: Cashier) {
        cashierDao.insert(cashier)
    }

    @WorkerThread
    suspend fun update(cashier: Cashier) {
        cashierDao.update(cashier)
    }

}