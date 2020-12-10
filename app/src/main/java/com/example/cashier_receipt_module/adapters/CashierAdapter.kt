package com.example.cashier_receipt_module.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.repository.models.Cashier

class CashierAdapter internal constructor(context: Context) : RecyclerView.Adapter<CashierAdapter.CashierViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cashiers = emptyList<Cashier>()

    inner class CashierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_paynment)
        val code: TextView = itemView.findViewById(R.id.label_code)
        val edit: ImageButton = itemView.findViewById(R.id.button_edit)
        val delete: ImageButton = itemView.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashierViewHolder {
        val itemView = inflater.inflate(R.layout.item_cashier, parent, false)
        return CashierViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CashierViewHolder, position: Int) {
        val current = cashiers[position]
        holder.name.text = current.Nombre
        holder.code.text = current.Codigo
    }

    internal fun setCashiers(cashiers: List<Cashier>) {
        this.cashiers = cashiers
        notifyDataSetChanged()
    }

    override fun getItemCount() = cashiers.size

}