package com.example.cashier_receipt_module.repository.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cashier_receipt_module.repository.dao.CashReceiptIncomeDao
import com.example.cashier_receipt_module.repository.models.CashReceiptIncome

class CashReceiptIncomeRepository(private val cash_receipt_dao: CashReceiptIncomeDao) {

    val allCashReceipts: LiveData<List<CashReceiptIncome>> = cash_receipt_dao.getAll()

    @WorkerThread
    suspend fun insert(cash_receipt: CashReceiptIncome) {
        cash_receipt_dao.insert(cash_receipt)
    }

    @WorkerThread
    suspend fun update(cash_receipt: CashReceiptIncome) {
        cash_receipt_dao.update(cash_receipt)
    }

}