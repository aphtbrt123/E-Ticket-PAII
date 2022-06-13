package com.kantin.e_ticket.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kantin.e_ticket.R
import com.kantin.e_ticket.model.Artefak

class AdapterArtefak(var data:ArrayList<Artefak>): RecyclerView.Adapter<AdapterArtefak.Holder>() {

    class Holder(view:View):RecyclerView.ViewHolder(view){
        val artefakNama = view.findViewById<TextView>(R.id.nama_artefak)
        val artefakDesc = view.findViewById<TextView>(R.id.desc_artefak)
        val artefakGambar = view.findViewById<ImageView>(R.id.img_artefak)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_artefak, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.artefakNama.text = data[position].nama
        holder.artefakDesc.text = data[position].shortDescription
        holder.artefakGambar.setImageResource(data[position].gambar)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}