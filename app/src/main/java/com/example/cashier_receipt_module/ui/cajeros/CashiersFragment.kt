package com.example.cashier_receipt_module.ui.cajeros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashier_receipt_module.R

class CashiersFragment : Fragment() {

    private lateinit var cashiersViewModel: CashiersViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cashiersViewModel =
                ViewModelProviders.of(this).get(CashiersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cashiers, container, false)
        return root
    }
}