package com.kantin.e_ticket.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kantin.e_ticket.MainActivity
import com.kantin.e_ticket.R
import com.kantin.e_ticket.activity.DetailTicketActivity
import com.kantin.e_ticket.activity.LoginActivity
import com.kantin.e_ticket.helper.Helper
import com.kantin.e_ticket.model.Artefak
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterArtefak(var activity: Activity, var data:ArrayList<Artefak>): RecyclerView.Adapter<AdapterArtefak.Holder>() {

    class Holder(view:View):RecyclerView.ViewHolder(view){
        val artefakNama = view.findViewById<TextView>(R.id.nama_artefak)
        val artefakDesc = view.findViewById<TextView>(R.id.desc_artefak)
        val artefakGambar = view.findViewById<ImageView>(R.id.img_artefak)
        val layout = view.findViewById<CardView>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_artefak, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.artefakNama.text = data[position].name
        holder.artefakDesc.text = Helper().gantiRupiah(data[position].harga)
//        holder.artefakGambar.setImageResource(data[position].image)

        val image = "http://192.168.43.104/tiket/public/storage/ticket/" + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.artefak_contoh)
            .error(R.drawable.artefak_contoh)
            .into(holder.artefakGambar)

        holder.layout.setOnClickListener{
            val activiti = Intent(activity, DetailTicketActivity::class.java)
            val str = Gson().toJson(data[position],Artefak::class.java)

            activiti.putExtra("extra", str)

            activity.startActivity(activiti)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}