package com.example.cashier_receipt_module.ui.cajeros

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashier_receipt_module.MainActivity
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.adapters.CashierAdapter
import com.example.cashier_receipt_module.repository.models.Cashier
import com.google.android.material.button.MaterialButton

class CashiersFragment : Fragment() {

    private lateinit var cashiersViewModel: CashiersViewModel
    private lateinit var cashierRecycler: RecyclerView
    private lateinit var cashierAdapter: CashierAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cashierAdapter = CashierAdapter(context!!)
        val root = inflater.inflate(R.layout.fragment_cashiers, container, false)
        initRecyclerCashiers(root)
        cashiersViewModel = ViewModelProvider(this).get(CashiersViewModel::class.java)
        cashiersViewModel.getAllCashiers().observe(viewLifecycleOwner, Observer { cashiers ->
            cashiers?.let { cashierAdapter.setCashiers(it) }
        })
        val cashierAddBtn = root.findViewById(R.id.add_cashier_btn) as MaterialButton
        cashierAddBtn.setOnClickListener {
            (activity as MainActivity).showDialogCashier()
        }
        return root
    }

    private fun initRecyclerCashiers(fragment: View) {
        cashierRecycler = fragment.findViewById(R.id.cashier_recycler) as RecyclerView
        cashierRecycler.adapter = cashierAdapter
        cashierRecycler.layoutManager = LinearLayoutManager(context)
    }

    fun addCashier(cashier: Cashier) {
        cashiersViewModel.insert(cashier)
    }

}