package com.example.cashier_receipt_module.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.repository.models.Cashier

class CashierAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<CashierAdapter.CashierViewHolder>() {

    private var cashiers = emptyList<Cashier>()
    private lateinit var cashiersRecycler: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashierViewHolder {
        return CashierViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cashier, parent, false))
    }

    override fun onBindViewHolder(holder: CashierViewHolder, position: Int) {
        holder.setData(cashiers[position])
    }

    override fun getItemCount(): Int {
        return cashiers.size
    }

    inner class CashierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name_cashier)
        fun setData(cashier: Cashier) {
            name.text = cashier.Nombre
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        cashiersRecycler = recyclerView
    }

    internal fun setCashiers(cashiers: List<Cashier>) {
        this.cashiers = cashiers
        notifyDataSetChanged()
    }
}