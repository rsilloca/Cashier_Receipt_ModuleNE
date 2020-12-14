package com.example.cashier_receipt_module.ui.clientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.adapters.ClientAdapter
import com.example.cashier_receipt_module.repository.models.Client
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText

class ClientsFragment : Fragment(), ClientAdapter.ClientListener {

    private lateinit var clientsViewModel: ClientsViewModel
    private lateinit var clientRecycler: RecyclerView
    private lateinit var clientAdapter: ClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        clientAdapter = ClientAdapter(requireContext(), this)
        clientsViewModel = ViewModelProvider(requireActivity()).get(ClientsViewModel::class.java)
        clientsViewModel.getAllClients().observe(viewLifecycleOwner, Observer { clients ->
            clients?.let { clientAdapter.setClients(it) }
        })
        val root = inflater.inflate(R.layout.fragment_clients,container,false)
        initRecyclerClients(root)

        val clientAddBtn = root.findViewById(R.id.add_clients_btn) as MaterialButton
        clientAddBtn.setOnClickListener {
            showDialogClient(false,Client(0,"","","A"))
        }
        return root
    }

    private fun initRecyclerClients(fragment: View) {
        clientRecycler = fragment.findViewById(R.id.clients_recycler) as RecyclerView
        clientRecycler.setHasFixedSize(true)
        clientRecycler.layoutManager = LinearLayoutManager(context)
        clientRecycler.adapter = clientAdapter
    }

    fun addClient(client: Client){
        clientsViewModel.insert(client)
    }

    fun updateClient(client: Client){
        clientsViewModel.update(client)
    }

    fun showDialogClient(edit: Boolean, client: Client) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_clients,null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        val codeEditText = dialogView.findViewById(R.id.input_code_client) as TextInputEditText
        val nameEditText = dialogView.findViewById(R.id.input_name_client) as TextInputEditText
        val toggleGroup = dialogView.findViewById(R.id.toggle_button_client) as MaterialButtonToggleGroup
        codeEditText.setText(client.Codigo)
        nameEditText.setText(client.Nombre)
        when (client.EstReg){
            "A" -> toggleGroup.check(R.id.active_client_btn)
            "I" -> toggleGroup.check(R.id.inactive_client_btn)
            else -> toggleGroup.check(R.id.deleted_client_btn)
        }
        val cancelButton = dialogView.findViewById(R.id.cancel_button_client) as Button
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        val saveButton = dialogView.findViewById(R.id.save_button_client) as Button
        saveButton.setOnClickListener {
            client.Codigo = codeEditText.text.toString()
            client.Nombre = nameEditText.text.toString()
            client.EstReg = when (toggleGroup.checkedButtonId){
                R.id.active_client_btn -> "A"
                R.id.inactive_client_btn -> "I"
                else -> "*"
            }
            if(edit) updateClient(client)
            else addClient(client)
            alertDialog.dismiss()
        }
    }
    fun delete (client: Client){
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
            client.EstReg = "*"
            updateClient(client)
            alertDialog.dismiss()
        }
    }

    override fun onEvent(type: Int, client: Client){
        if(type == 1 ) showDialogClient(true,client)
        else delete(client)
    }
}