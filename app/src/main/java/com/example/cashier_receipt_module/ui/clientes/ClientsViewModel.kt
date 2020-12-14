package com.example.cashier_receipt_module.ui.clientes

import android.app.Application
import androidx.lifecycle.*
import com.example.cashier_receipt_module.repository.CashierReceiptModuleDatabase
import com.example.cashier_receipt_module.repository.models.Client
import com.example.cashier_receipt_module.repository.repositories.ClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientsViewModel (application: Application): AndroidViewModel (application){

    private val clientRepository: ClientRepository
    private val allClients: LiveData<List<Client>>

    init {
        val clientDao = CashierReceiptModuleDatabase.getDatabase(application).clientDao()
        clientRepository = ClientRepository(clientDao)
        allClients = clientRepository.allClients
    }

    fun insert(client: Client) = viewModelScope.launch(Dispatchers.IO) {
        clientRepository.insert(client)
    }
    fun update(client: Client) = viewModelScope.launch(Dispatchers.IO){
        clientRepository.update(client)
    }

    fun getAllClients() = allClients

}