package com.example.cashier_receipt_module.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.adapters.CashReceiptIncomeAdapter
import com.example.cashier_receipt_module.repository.models.CashReceiptIncome
import com.example.cashier_receipt_module.repository.models.Cashier
import com.example.cashier_receipt_module.repository.models.Client
import com.example.cashier_receipt_module.ui.clientes.ClientsViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment(), CashReceiptIncomeAdapter.CashReceiptIncomeListener {

    private lateinit var homeViewModel: HomeViewModel
    private var cashiersName = mutableMapOf<Long, String>()

    private var clientsName = mutableMapOf<Long, String>()

    private lateinit var homeRecycler: RecyclerView
    private lateinit var homeAdapter: CashReceiptIncomeAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homeAdapter = CashReceiptIncomeAdapter(requireContext(),this)


        homeViewModel =  ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initRecyclerPayments(root)

        // Agregar cajeros al map
        homeViewModel.getAllCashiers().observe(viewLifecycleOwner, Observer { cashiers ->
            cashiers?.let {
                for (cashier in it) {
                    cashiersName[cashier.Id] = cashier.Nombre
                }
            }
        })
        // Fin agregar cajeros al map


        // Agregar clientes al map
        homeViewModel.getAllClients().observe(viewLifecycleOwner, Observer { clients ->
            clients?.let {
                for (client in it) {
                    clientsName[client.Id] = client.Nombre
                }
            }
        })
        // Fin agregar clientes al map

        homeViewModel.getAllCashReceipts().observe(viewLifecycleOwner, Observer { payments ->
            payments?.let{homeAdapter.setCashReceiptIncomes(it)}
        })

        val addBtn = root.findViewById(R.id.add_pay_btn) as MaterialButton
        addBtn.setOnClickListener {
            showDialogPayment(false, CashReceiptIncome(0, 1, 1,0,0.0, "A"))
        }
        return root
    }

    private fun initRecyclerPayments(fragment: View){
        homeRecycler = fragment.findViewById(R.id.pay_recycler) as RecyclerView
        homeRecycler.setHasFixedSize(true)
        homeRecycler.layoutManager = LinearLayoutManager(context)
        homeRecycler.adapter = homeAdapter
    }

    fun addPayment(payment: CashReceiptIncome){
        homeViewModel.insert(payment)
    }

    fun updatePayment(payment: CashReceiptIncome){
        homeViewModel.update(payment)
    }

    fun showDialogPayment(edit: Boolean, payment: CashReceiptIncome) {
        if (cashiersName.isEmpty() || clientsName.isEmpty()) {
            val forget = if (cashiersName.isEmpty()) "Cajeros" else "Clientes"
            AlertDialog.Builder(requireContext())
                .setTitle("Cuidado")
                .setMessage("Debe agregar " + forget + ".")
                .setPositiveButton("Aceptar", null)
                .show()
            return
        }
        val dialogView = layoutInflater.inflate(R.layout.dialog_paynment,null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        // Listar cajeros en spinner
        val cashiersNameAdapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_popup_item, cashiersName.values.toList())
        val spinnerCashier = dialogView.findViewById(R.id.cashier_filled_exposed_dropdown) as AutoCompleteTextView
        spinnerCashier.setAdapter(cashiersNameAdapter)
        spinnerCashier.setOnItemClickListener { _, _, position, _ ->
            payment.IdCashier = cashiersName.keys.elementAt(position)
        }
        if (edit) spinnerCashier.setText(cashiersName[payment.IdCashier])
        // Fin listar cajeros en spinner
        // Listar clientes en spinner
        val clientsNameAdapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_menu_popup_item, clientsName.values.toList())
        val spinnerClient = dialogView.findViewById(R.id.client_filled_exposed_dropdown) as AutoCompleteTextView
        spinnerClient.setAdapter(clientsNameAdapter)
        spinnerClient.setOnItemClickListener { _, _, position, _ ->
            payment.IdClient = clientsName.keys.elementAt(position)
        }
        if (edit) spinnerClient.setText(clientsName[payment.IdClient])
        // Fin listar clientes en spinner

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


        val cancelButton = dialogView.findViewById(R.id.cancel_button) as Button
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        val saveButton = dialogView.findViewById(R.id.save_button) as Button
        saveButton.setOnClickListener {

            payment.Numero = numberEditText.text.toString().toInt()
            payment.Monto = amountEditText.text.toString().toDouble()
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

    fun delete (cashReceiptIncome: CashReceiptIncome){
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm_delete,null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        val cancelButton = dialogView.findViewById(R.id.cancel_button) as Button
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        val saveButton = dialogView.findViewById(R.id.save_button) as Button
        saveButton.setOnClickListener {
            cashReceiptIncome.EstReg = "*"
            updatePayment(cashReceiptIncome)
            alertDialog.dismiss()
        }
    }

    override fun onEvent(type: Int, cashReceiptIncome: CashReceiptIncome){
        if(type == 1) showDialogPayment(true,cashReceiptIncome)
        else delete (cashReceiptIncome)
    }


}