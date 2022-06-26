package com.kantin.e_ticket.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kantin.e_ticket.R
import com.kantin.e_ticket.helper.Helper
import com.kantin.e_ticket.model.Artefak
import com.kantin.e_ticket.room.MyDatabase
import com.kantin.e_ticket.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class AdapterKeranjang(var activity: Activity, var data:ArrayList<Artefak>, var listener: Listeners) : RecyclerView.Adapter<AdapterKeranjang.Holder>() {

    class Holder(view:View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)

        val btnTambah = view.findViewById<ImageView>(R.id.btn_tambah)
        val btnKurang = view.findViewById<ImageView>(R.id.btn_kurang)

        val btnDelete = view.findViewById<ImageView>(R.id.btn_delete)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_keranjang, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val ticket = data[position]
        val harga = Integer.valueOf(ticket.harga)


        holder.tvNama.text = ticket.name
        holder.tvHarga.text = Helper().gantiRupiah(harga * ticket.jumlah)


        var jumlah = data[position].jumlah
        holder.tvJumlah.text = jumlah.toString()

        holder.checkBox.isChecked = ticket.selected
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            ticket.selected = isChecked
            update(ticket)
        }

        val image = Config.ticketUrl + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.artefak_contoh)
            .error(R.drawable.artefak_contoh)
            .into(holder.imgProduk)

        holder.btnTambah.setOnClickListener {

            jumlah++
            ticket.jumlah = jumlah
            update(ticket)

            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)
        }

        holder.btnKurang.setOnClickListener {
            if(jumlah <= 1)return@setOnClickListener


            jumlah--

            ticket.jumlah = jumlah
            update(ticket)

            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)


        }

        holder.btnDelete.setOnClickListener {
            delete(ticket)
            listener.onDelete(position)
        }

    }

    interface Listeners {
        fun onUpdate()
        fun onDelete(position: Int)

    }


    override fun getItemCount(): Int {
        return data.size
    }

    private fun update(data: Artefak){
        val myDb = MyDatabase.getInstance(activity)

        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoListTicket().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    private fun delete(data: Artefak){
        val myDb = MyDatabase.getInstance(activity)

        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoListTicket().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            })
    }

}