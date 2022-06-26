package com.kantin.e_ticket.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.inyongtisto.tokoonline.app.ApiConfig
import com.kantin.e_ticket.R
import com.kantin.e_ticket.adapter.AdapterTransaksi
import com.kantin.e_ticket.databinding.ActivityListTransaksiBinding
import com.kantin.e_ticket.helper.SharedPref
import com.kantin.e_ticket.model.User
import com.kantin.e_ticket.model.transaksi.Transaksi
import com.kantin.e_ticket.model.transaksi.TransaksiResponModel
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListTransaksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListTransaksiBinding

    lateinit var s: SharedPref
    lateinit var user: User
    lateinit var adapter: AdapterTransaksi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        getListTransaksi()
    }

    override fun onResume() {
        super.onResume()
        getListTransaksi()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUp() {
        s = SharedPref(this)
        user = s.getUser()!!

        //set toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "List Transaksi"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getListTransaksi() {
        binding.pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.getTransaksi(user.id).enqueue(object : Callback<TransaksiResponModel> {
            override fun onResponse(
                call: Call<TransaksiResponModel>,
                response: Response<TransaksiResponModel>
            ) {
                val respon = response.body()!!

                if(respon.success == 1) {
                    adapter = AdapterTransaksi(
                        respon.listTransaksi!!,
                        listener = { transaksi: Transaksi ->
                            onClickTransaksi(transaksi)
                        }
                    )
                    binding.rvTransaksi.adapter = adapter
                    binding.rvTransaksi.layoutManager = LinearLayoutManager(this@ListTransaksiActivity)
                }
            }

            override fun onFailure(call: Call<TransaksiResponModel>, t: Throwable) {
                Toast.makeText(applicationContext, "Error " + t.message, Toast.LENGTH_LONG).show()
            }

        })
        binding.pb.visibility = View.GONE
    }

    private fun onClickTransaksi(transaksi: Transaksi) {
        startActivity(
            Intent(this, DetailTransaksiActivity::class.java)
                .putExtra(DetailTransaksiActivity.EXTRA_ID, transaksi.id)
        )
    }
}