package com.example.cashier_receipt_module.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.repository.models.CashReceiptIncome
import com.example.cashier_receipt_module.repository.models.Cashier
import com.example.cashier_receipt_module.repository.models.Client
import com.example.cashier_receipt_module.ui.clientes.ClientsViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var cashiersName = mutableMapOf<String, Long>();

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =  ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        // Agregar cajeros al map
        homeViewModel.getAllCashiers().observe(viewLifecycleOwner, Observer { cashiers ->
            cashiers?.let {
                for (cashier in it) {
                    cashiersName[cashier.Nombre] = cashier.Id
                }
            }
        })
        // Fin agregar cajeros al map
        val addBtn = root.findViewById(R.id.add_pay_btn) as MaterialButton
        addBtn.setOnClickListener {
            showDialogPayment(false, CashReceiptIncome(0, 1, 1,0,0.0, "A"))
        }
        return root
    }

    fun addPayment(payment: CashReceiptIncome){
        homeViewModel.insert(payment)
    }

    fun updatePayment(payment: CashReceiptIncome){
        homeViewModel.update(payment)
    }

    fun showDialogPayment(edit: Boolean, payment: CashReceiptIncome) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_paynment,null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        // Listar cajeros en spinner
        val cashiersNameAdapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_popup_item, cashiersName.keys.toList())
        val spinnerCashier = dialogView.findViewById(R.id.cashier_filled_exposed_dropdown) as AutoCompleteTextView
        spinnerCashier.setAdapter(cashiersNameAdapter)
        // Fin listar cajeros en spinner
        val numberEditText = dialogView.findViewById(R.id.input_number_payment) as TextInputEditText
        val amountEditText = dialogView.findViewById(R.id.input_amount_payment) as TextInputEditText
        val toggleGroup = dialogView.findViewById(R.id.toggle_button_payment) as MaterialButtonToggleGroup
        numberEditText.setText(payment.Numero.toString())
        amountEditText.setText(payment.Monto.toString())
        when (payment.EstReg){
            "A" -> toggleGroup.check(R.id.active_payment_btn)
            "I" -> toggleGroup.check(R.id.inactive_payment_btn)
            else -> toggleGroup.check(R.id.deleted_payment_btn)
        }
        val cancelButton = dialogView.findViewById(R.id.cancel_button) as MaterialButton
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        val saveButton = dialogView.findViewById(R.id.save_button) as MaterialButton
        saveButton.setOnClickListener {
            payment.Numero = numberEditText.text.toString() as Int
            payment.Monto = amountEditText.text.toString() as Double
            // Asignacion de id Cajero, genera bug cuando dos cajeros tienen el mismo nombre xd
            payment.IdCashier = cashiersName[spinnerCashier.text.toString()]!!
            // Fin asignacion de id Cajero
            payment.EstReg = when (toggleGroup.checkedButtonId){
                R.id.active_payment_btn -> "A"
                R.id.inactive_payment_btn -> "I"
                else -> "*"
            }
            if(edit) updatePayment(payment)
            else addPayment(payment)
            alertDialog.dismiss()
        }
    }

}