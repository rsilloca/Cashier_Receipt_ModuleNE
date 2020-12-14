package com.example.cashier_receipt_module.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.repository.models.Client

class ClientAdapter internal constructor(private val context : Context, private val listener: ClientListener) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var  recyclerClients: RecyclerView
    private var clients = emptyList<Client>()

    class ClientViewHolder(private val itemView: View, private val listener: ClientListener) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_client)
        val code: TextView = itemView.findViewById(R.id.code_client)
        val edit: ImageButton = itemView.findViewById(R.id.button_edit);
        val delete: ImageButton = itemView.findViewById(R.id.button_delete);

        fun bind(client : Client){
            name.text = client.Nombre;
            code.text = client.Codigo;
            edit.setOnClickListener{
                listener.onEvent(1,client)
            }
            delete.setOnClickListener {
                listener.onEvent(2,client)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val itemView = inflater.inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val current = clients[position]
        holder.bind(current)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerClients = recyclerView
    }

    override fun getItemCount() = clients.size;

    internal fun setClients(clients: List<Client>){
        this.clients = clients
        notifyDataSetChanged()
    }
    public interface ClientListener {
        fun onEvent(type: Int, client:Client)
    }
}

