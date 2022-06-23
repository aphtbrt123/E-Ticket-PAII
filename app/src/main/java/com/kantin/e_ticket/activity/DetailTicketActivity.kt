package com.kantin.e_ticket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.kantin.e_ticket.R
import com.kantin.e_ticket.helper.Helper
import com.kantin.e_ticket.model.Artefak
import com.kantin.e_ticket.model.listTicket
import com.kantin.e_ticket.room.MyDatabase
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

    fun mainButton(){
        btn_keranjang.setOnClickListener {
            insert()
        }

        btn_favorit.setOnClickListener {

            val listData = myDb.daoListTicket().getAll() // get All data
            for(note :Artefak in listData){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }
    }


    private fun insert(){

        CompositeDisposable().add(Observable.fromCallable { myDb.daoListTicket().insert(ticket) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkListTicket()
                Log.d("respons", "data inserted")
            })
    }

    private fun checkListTicket(){
        var dataListTicket = myDb.daoListTicket().getAll()

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


        val img = "http://192.168.43.104/tiket/public/storage/ticket/" + ticket.image
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