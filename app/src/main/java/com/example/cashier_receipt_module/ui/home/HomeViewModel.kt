package com.example.cashier_receipt_module.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.cashier_receipt_module.repository.CashierReceiptModuleDatabase
import com.example.cashier_receipt_module.repository.models.CashReceiptIncome
import com.example.cashier_receipt_module.repository.models.Cashier
import com.example.cashier_receipt_module.repository.models.Client
import com.example.cashier_receipt_module.repository.repositories.CashReceiptIncomeRepository
import com.example.cashier_receipt_module.repository.repositories.CashierRepository
import com.example.cashier_receipt_module.repository.repositories.ClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val cashierRepository: CashierRepository
    private val allCashiers: LiveData<List<Cashier>>
    private val clientRepository: ClientRepository
    private val allClients: LiveData<List<Client>>
    private val cashReceiptRepository: CashReceiptIncomeRepository
    private val allCashReceipts: LiveData<List<CashReceiptIncome>>

    init {
        val cashierDao = CashierReceiptModuleDatabase.getDatabase(application).cashierDao()
        cashierRepository = CashierRepository(cashierDao)
        allCashiers = cashierRepository.allCashiers
        val clientDao = CashierReceiptModuleDatabase.getDatabase(application).clientDao()
        clientRepository = ClientRepository(clientDao)
        allClients = clientRepository.allClients
        val cashReceiptDao = CashierReceiptModuleDatabase.getDatabase(application).cashReceiptDao()
        cashReceiptRepository = CashReceiptIncomeRepository(cashReceiptDao)
        allCashReceipts = cashReceiptRepository.allCashReceipts
    }

    fun insert(payment: CashReceiptIncome) = viewModelScope.launch(Dispatchers.IO) {
        cashReceiptRepository.insert(payment)
    }

    fun update(payment: CashReceiptIncome) = viewModelScope.launch(Dispatchers.IO) {
        cashReceiptRepository.update(payment)
    }

    fun getAllCashiers() = allCashiers
    fun getAllClients() = allClients
    fun getAllCashReceipts() = allCashReceipts
}