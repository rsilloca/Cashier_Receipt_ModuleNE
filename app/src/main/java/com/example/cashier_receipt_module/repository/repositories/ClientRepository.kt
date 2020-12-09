package com.example.cashier_receipt_module.repository.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cashier_receipt_module.repository.dao.ClientDao
import com.example.cashier_receipt_module.repository.models.Client

class ClientRepository(private val clientDao: ClientDao) {

    val allClients: LiveData<List<Client>> = clientDao.getAll()

    @WorkerThread
    suspend fun insert(client: Client) {
        clientDao.insert(client)
    }

    @WorkerThread
    suspend fun update(client: Client) {
        clientDao.update(client)
    }

}