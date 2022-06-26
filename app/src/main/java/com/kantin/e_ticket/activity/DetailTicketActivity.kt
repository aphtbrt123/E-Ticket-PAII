package com.kantin.e_ticket.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.kantin.e_ticket.R
import com.kantin.e_ticket.helper.Helper
import com.kantin.e_ticket.model.Artefak
import com.kantin.e_ticket.model.listTicket
import com.kantin.e_ticket.room.MyDatabase
import com.kantin.e_ticket.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_ticket.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailTicketActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase
    lateinit var ticket: Artefak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ticket)
        myDb = MyDatabase.getInstance(this)!!

        getInfo()
        mainButton()
        checkListTicket()
    }

    private fun mainButton(){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoListTicket().getNote(ticket.id)
            if(data == null){
                insert()
            }else{
                ticket.jumlah = data.jumlah + 1
                update(data)
            }


        }

        btn_favorit.setOnClickListener {

            val listData = myDb.daoListTicket().getAll() // get All data
            for(note :Artefak in listData){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }

        btn_toKeranjang.setOnClickListener {
            val intent = Intent("event:listTicket")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    private fun update(data: Artefak){

        CompositeDisposable().add(Observable.fromCallable { myDb.daoListTicket().update(ticket) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkListTicket()
                Log.d("respons", "data updated")
                Toast.makeText(this,"Berhasil mengubah list tiket anda!", Toast.LENGTH_SHORT).show()
            })
    }


    private fun insert(){

        CompositeDisposable().add(Observable.fromCallable { myDb.daoListTicket().insert(ticket) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkListTicket()
                Log.d("respons", "data inserted")
                Toast.makeText(this,"Berhasil ditambah ke list tiket anda!", Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkListTicket(){
        val dataListTicket = myDb.daoListTicket().getAll()

        if (dataListTicket.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataListTicket.size.toString()
        }else{
            div_angka.visibility = View.GONE
        }
    }

    fun getInfo(){
        val data = intent.getStringExtra("extra")
        ticket = Gson().fromJson<Artefak>(data, Artefak::class.java)

        //set value
        tv_nama.text = ticket.name
        tv_harga.text = Helper().gantiRupiah(ticket.harga)
        tv_deskripsi.text = ticket.deskripsi


        val img = Config.ticketUrl + ticket.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.artefak_contoh)
            .error(R.drawable.artefak_contoh)
            .resize(400,400)
            .into(image)


        //set toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ticket.name
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}