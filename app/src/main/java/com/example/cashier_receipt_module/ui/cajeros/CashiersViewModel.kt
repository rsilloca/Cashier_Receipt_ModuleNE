package com.example.cashier_receipt_module.ui.cajeros

import android.app.Application
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
        val cashierDao = CashierReceiptModuleDatabase.getDatabase(application).cashierDao()
        cashierRepository = CashierRepository(cashierDao)
        allCashiers = cashierRepository.allCashiers
    }

    fun insert(cashier: Cashier) = viewModelScope.launch(Dispatchers.IO) {
        cashierRepository.insert(cashier)
    }

    fun update(cashier: Cashier) = viewModelScope.launch(Dispatchers.IO) {
        cashierRepository.update(cashier)
    }

    fun getAllCashiers() = allCashiers
}