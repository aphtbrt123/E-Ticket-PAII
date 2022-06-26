package com.kantin.e_ticket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kantin.e_ticket.R
import com.kantin.e_ticket.databinding.ActivitySummaryTransaksiBaruBinding
import com.kantin.e_ticket.model.Artefak

class SummaryTransaksiBaruActivity : AppCompatActivity() {
    lateinit var binding: ActivitySummaryTransaksiBaruBinding
    lateinit var listTicketTransaksi: ArrayList<Artefak>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryTransaksiBaruBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    private fun setUp() {
        listTicketTransaksi = intent.getSerializableExtra(EXTRA_LIST_TICKET) as ArrayList<Artefak>
        Log.d("MyTag", "listTicketTransaksi: ${listTicketTransaksi[0].id}")
    }

    companion object {
        const val EXTRA_LIST_TICKET = "extra_list_ticket"
    }
}