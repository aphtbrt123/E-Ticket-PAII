package com.kantin.e_ticket.activity

import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.inyongtisto.tokoonline.app.ApiConfig
import com.kantin.e_ticket.R
import com.kantin.e_ticket.databinding.ActivityDetailTransaksiBinding
import com.kantin.e_ticket.fragment.BuktiTransferTransaksiFragment
import com.kantin.e_ticket.model.transaksi.Transaksi
import com.kantin.e_ticket.model.transaksi.TransaksiResponModel
import com.kantin.e_ticket.util.Config
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_ticket.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import kotlinx.android.synthetic.main.toolbar_custom.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTransaksiActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailTransaksiBinding

    var idTransaksi: Int = 0
    lateinit var transaksi: Transaksi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    override fun onResume() {
        super.onResume()
        getTransaksi()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUp() {
        idTransaksi = intent.getIntExtra(EXTRA_ID, 0)
        if (idTransaksi == 0) {
            finish()
        }

        //set toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Detail Transaksi"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getTransaksi()
    }

    fun getTransaksi() {
        binding.pb.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.getDetailTransaksi(idTransaksi)
            .enqueue(object : Callback<TransaksiResponModel> {
                override fun onResponse(
                    call: Call<TransaksiResponModel>,
                    response: Response<TransaksiResponModel>
                ) {
                    val respon = response.body()!!

                    if(respon.success == 1) {
                        transaksi = respon.transaksi!!
                        displayTransaksi()
                    }
                }

                override fun onFailure(call: Call<TransaksiResponModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error " + t.message, Toast.LENGTH_LONG).show()
                }

            })

        binding.pb.visibility = View.GONE
    }

    private fun displayTransaksi() {
        binding.judulTransaksi.text = transaksi.kodeTrx
        binding.totalHargaTransaksi.text = "Rp. ${transaksi.totalHarga}"
        binding.statusTransaksi.text = "Status \t: ${transaksi.status}"
        binding.nameTransaksi.text = "Nama Pemesan \t: ${transaksi.name}"
        binding.phoneTransaksi.text = "No. HP Pemesan \t: ${transaksi.phone}"
        binding.tanggalBerangkatTransaksi.text = "Tanggal Pemesanan \t: ${transaksi.detailTanggal}"

        if(transaksi.buktiTransfer == null) {
            binding.jumlahTransfer.text = "Anda belum mengunggah bukti transfer"
        }
        else {
            binding.jumlahTransfer.text = "Rp. ${transaksi.totalTransfer}"
            val img = Config.baseUrl + transaksi.buktiTransfer
            Picasso.get()
                .load(img)
                .placeholder(R.drawable.logo_tiket)
                .error(R.drawable.logo_tiket)
                .into(binding.imageBuktiTransfer)
        }

        binding.buttonKirimBuktiTransfer.setOnClickListener {
            BuktiTransferTransaksiFragment().show(supportFragmentManager, BuktiTransferTransaksiFragment.TAG)
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }

}