package com.kantin.e_ticket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kantin.e_ticket.databinding.ActivityListTransaksiBinding
import com.kantin.e_ticket.databinding.ListItemTransaksiBinding
import com.kantin.e_ticket.model.transaksi.Transaksi

class AdapterTransaksi(
    var data: List<Transaksi>,
    val listener: (Transaksi) -> Unit
) : RecyclerView.Adapter<TransaksiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val binding =
            ListItemTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransaksiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class TransaksiViewHolder(val binding: ListItemTransaksiBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(transaksi: Transaksi, listener: (Transaksi) -> Unit) {
        binding.judulTransaksi.text = "Transaksi ${transaksi.kodeTrx}"
        binding.totalHargaTransaksi.text = "Rp. ${transaksi.totalHarga}"
        binding.tanggalTransaksiDibuat.text = "Dipesan pada ${transaksi.createdAt}"
        binding.statusTransaksi.text = transaksi.status

        binding.root.setOnClickListener {
            listener(transaksi)
        }
    }

}