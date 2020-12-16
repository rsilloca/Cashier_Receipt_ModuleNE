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

class CashierAdapter internal constructor(private val context: Context, private val listener: CashierListener) : RecyclerView.Adapter<CashierAdapter.CashierViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var recyclerCashiers: RecyclerView
    private var cashiers = emptyList<Cashier?>()

    class CashierViewHolder(private val itemView: View, private val listener: CashierListener) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_cashier)
        val code: TextView = itemView.findViewById(R.id.code_cashier)
        val edit: ImageButton = itemView.findViewById(R.id.button_edit)
        val delete: ImageButton = itemView.findViewById(R.id.button_delete)

        fun bind(cashier: Cashier) {
            name.text = cashier.Nombre
            code.text = cashier.Codigo
            edit.setOnClickListener {
                listener.onEvent(1, cashier)
            }
            delete.setOnClickListener {
                listener.onEvent(2, cashier)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashierViewHolder {
        val itemView = inflater.inflate(R.layout.item_cashier, parent, false)
        return CashierViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: CashierViewHolder, position: Int) {
        val current = cashiers[position]
        if (current != null) {
            holder.bind(current)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerCashiers = recyclerView
    }

    override fun getItemCount() = cashiers.size

    internal fun setCashiers(cashiers: List<Cashier?>) {
        this.cashiers = cashiers
        notifyDataSetChanged()
    }

    public interface CashierListener {
        fun onEvent(type: Int, cashier: Cashier)
    }

}