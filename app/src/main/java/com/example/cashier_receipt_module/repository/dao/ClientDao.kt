package com.example.cashier_receipt_module.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cashier_receipt_module.repository.models.Client

@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(client: Client)

    @Update
    fun update(client: Client)

    @Query("SELECT * FROM clients WHERE Id = :key")
    fun getClient(key: Long): LiveData<Client?>

    @Query("SELECT * FROM clients")
    fun getAll(): LiveData<List<Client>>

    @Query("DELETE FROM clients")
    fun clear()
}