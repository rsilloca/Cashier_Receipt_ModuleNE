package com.example.cashier_receipt_module.ui.cajeros

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.cashier_receipt_module.repository.CashierReceiptModuleDatabase
import com.example.cashier_receipt_module.repository.models.Cashier
import com.example.cashier_receipt_module.repository.repositories.CashierRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CashiersViewModel(application: Application) : AndroidViewModel(application) {

    private val cashierRepository: CashierRepository
    private val allCashiers: LiveData<List<Cashier>>

    init {
        val semestersDao = CashierReceiptModuleDatabase.getDatabase(application).cashierDao()
        cashierRepository = CashierRepository(semestersDao)
        allCashiers = cashierRepository.allCashiers
    }

    fun insert(cashier: Cashier) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("Agregando", "Repository")
        cashierRepository.insert(cashier)
    }

    fun getAllCashiers() = allCashiers
}