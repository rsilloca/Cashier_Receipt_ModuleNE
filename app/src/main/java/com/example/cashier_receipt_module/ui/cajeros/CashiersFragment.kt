package com.example.cashier_receipt_module.ui.cajeros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.adapters.CashierAdapter
import com.example.cashier_receipt_module.repository.models.Cashier
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText

class CashiersFragment : Fragment(), CashierAdapter.CashierListener {

    private lateinit var cashiersViewModel: CashiersViewModel
    private lateinit var cashierRecycler: RecyclerView
    private lateinit var cashierAdapter: CashierAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cashierAdapter = CashierAdapter(requireContext(), this)
        cashiersViewModel = ViewModelProvider(requireActivity()).get(CashiersViewModel::class.java)
        cashiersViewModel.getAllCashiers().observe(viewLifecycleOwner, Observer { cashiers ->
            cashiers?.let { cashierAdapter.setCashiers(it) }
        })
        val root = inflater.inflate(R.layout.fragment_cashiers, container, false)
        initRecyclerCashiers(root)

        val cashierAddBtn = root.findViewById(R.id.add_cashier_btn) as MaterialButton
        cashierAddBtn.setOnClickListener {
            showDialogCashier(false, Cashier(0,"","","A"))
        }
        return root
    }

    private fun initRecyclerCashiers(fragment: View) {
        cashierRecycler = fragment.findViewById(R.id.cashier_recycler) as RecyclerView
        cashierRecycler.setHasFixedSize(true)
        cashierRecycler.layoutManager = LinearLayoutManager(context)
        cashierRecycler.adapter = cashierAdapter
    }

    fun addCashier(cashier: Cashier) {
        cashiersViewModel.insert(cashier)
    }

    fun updateCashier(cashier: Cashier) {
        cashiersViewModel.update(cashier)
    }

    fun showDialogCashier(edit: Boolean, cashier: Cashier) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_cashier, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        val codeEditText = dialogView.findViewById(R.id.input_code_cashier) as TextInputEditText
        val nameEditText = dialogView.findViewById(R.id.input_name_cashier) as TextInputEditText
        val toggleGroup = dialogView.findViewById(R.id.toggle_button_cashier) as MaterialButtonToggleGroup
        codeEditText.setText(cashier.Codigo)
        nameEditText.setText(cashier.Nombre)
        when (cashier.EstReg) {
            "A" -> toggleGroup.check(R.id.active_cashier_btn)
            "I" -> toggleGroup.check(R.id.inactive_cashier_btn)
            else -> toggleGroup.check(R.id.deleted_cashier_btn)
        }
        val cancelButton = dialogView.findViewById(R.id.cancel_button) as Button
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        val saveButton = dialogView.findViewById(R.id.save_button) as Button
        saveButton.setOnClickListener {
            cashier.Codigo = codeEditText.text.toString()
            cashier.Nombre = nameEditText.text.toString()
            cashier.EstReg = when (toggleGroup.checkedButtonId) {
                R.id.active_cashier_btn -> "A"
                R.id.inactive_cashier_btn -> "I"
                else -> "*"
            }
            if (edit) updateCashier(cashier)
            else addCashier(cashier)
            alertDialog.dismiss()
        }
    }

    fun delete(cashier: Cashier) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm_delete, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        val cancelButton = dialogView.findViewById(R.id.cancel_button) as Button
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        val saveButton = dialogView.findViewById(R.id.save_button) as Button
        saveButton.setOnClickListener {
            cashier.EstReg = "*"
            updateCashier(cashier)
            alertDialog.dismiss()
        }
    }

    override fun onEvent(type: Int, cashier: Cashier) {
        if (type == 1) showDialogCashier(true, cashier)
        else delete(cashier)
    }

}