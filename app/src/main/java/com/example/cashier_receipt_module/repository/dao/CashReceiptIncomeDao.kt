package com.example.cashier_receipt_module.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cashier_receipt_module.repository.models.CashReceiptIncome

@Dao
interface CashReceiptIncomeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cash_receipt: CashReceiptIncome)

    @Update
    fun update(cash_receipt: CashReceiptIncome)

    @Query("SELECT * FROM cash_receipts_income WHERE Id = :key")
    fun getCashReceiptIncome(key: Long): LiveData<CashReceiptIncome?>

    @Query("SELECT * FROM cash_receipts_income")
    fun getAll(): LiveData<List<CashReceiptIncome>>

    @Query("DELETE FROM cash_receipts_income")
    fun clear()
}