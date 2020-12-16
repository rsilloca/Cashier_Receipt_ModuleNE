package com.example.cashier_receipt_module.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashier_receipt_module.R
import com.example.cashier_receipt_module.repository.models.CashReceiptIncome

class CashReceiptIncomeAdapter internal constructor(private val context : Context, private val listener: CashReceiptIncomeListener) : RecyclerView.Adapter<CashReceiptIncomeAdapter.CashReceiptIncomeViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var recyclerCashReceiptIncomes: RecyclerView
    private var cashReceiptIncomes = emptyList<CashReceiptIncome>()

    class CashReceiptIncomeViewHolder (private val itemView: View, private val listener: CashReceiptIncomeListener) : RecyclerView.ViewHolder(itemView){

        val name: TextView = itemView.findViewById(R.id.name_paynment)
        val code: TextView = itemView.findViewById(R.id.label_code)
        val edit: ImageButton = itemView.findViewById(R.id.button_edit_payment)
        val delete: ImageButton = itemView.findViewById(R.id.button_delete_payment)

        fun bind(cashReceiptIncome: CashReceiptIncome){
            name.text = cashReceiptIncome.Monto.toString();
            code.text = cashReceiptIncome.Numero.toString();
            edit.setOnClickListener {
                listener.onEvent(1,cashReceiptIncome)
            }
            delete.setOnClickListener {
                listener.onEvent(2,cashReceiptIncome)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashReceiptIncomeViewHolder {
        val itemView = inflater.inflate(R.layout.item_pay,parent,false)
        return CashReceiptIncomeViewHolder(itemView,listener)
    }

    override fun onBindViewHolder(holder: CashReceiptIncomeViewHolder, position: Int) {
        val current = cashReceiptIncomes[position]
        holder.bind(current)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerCashReceiptIncomes = recyclerView
    }

    override fun getItemCount() = cashReceiptIncomes.size;

    internal fun setCashReceiptIncomes(cashReceiptIncomes: List<CashReceiptIncome>){
        this.cashReceiptIncomes = cashReceiptIncomes;
        notifyDataSetChanged()
    }
    public interface CashReceiptIncomeListener{
        fun onEvent(type: Int, cashReceiptIncome: CashReceiptIncome)
    }
}