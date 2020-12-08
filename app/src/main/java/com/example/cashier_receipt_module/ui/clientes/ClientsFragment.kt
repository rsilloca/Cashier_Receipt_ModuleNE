package com.example.cashier_receipt_module.ui.clientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashier_receipt_module.R

class ClientsFragment : Fragment() {

    private lateinit var clientsViewModel: ClientsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        clientsViewModel =
                ViewModelProviders.of(this).get(ClientsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_clients, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        clientsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}