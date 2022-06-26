package com.kantin.e_ticket.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.inyongtisto.tokoonline.app.ApiConfig
import com.kantin.e_ticket.R
import com.kantin.e_ticket.activity.DetailTransaksiActivity
import com.kantin.e_ticket.activity.SummaryTransaksiBaruActivity
import com.kantin.e_ticket.adapter.AdapterKeranjang
import com.kantin.e_ticket.databinding.FragmentTiketBinding
import com.kantin.e_ticket.helper.Helper
import com.kantin.e_ticket.helper.SharedPref
import com.kantin.e_ticket.model.Artefak
import com.kantin.e_ticket.model.NewTransaksi
import com.kantin.e_ticket.model.User
import com.kantin.e_ticket.model.transaksi.TransaksiResponModel
import com.kantin.e_ticket.room.MyDatabase
import kotlinx.android.synthetic.main.fragment_tiket.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.tan

class TiketFragment : Fragment() {
    lateinit var binding: FragmentTiketBinding

    lateinit var myDb: MyDatabase
    lateinit var s: SharedPref
    lateinit var user: User

    val listTicketTransaksi = ArrayList<Artefak>()

    var tanggal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            myDb = MyDatabase.getInstance(requireActivity())!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTiketBinding.inflate(inflater, container, false)
        val view: View = inflater.inflate(R.layout.fragment_tiket, container, false)
        init(view)

        myDb = MyDatabase.getInstance(requireActivity())!!
        s = SharedPref(requireActivity())
        user = s.getUser()!!

        mainButton()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_tanggal.setOnClickListener {
            startAndGetValueDatePicker()
        }
        tv_tanggal_berkunjung.setOnClickListener {
            startAndGetValueDatePicker()
        }
    }

    lateinit var adapter: AdapterKeranjang
    var listTicket = ArrayList<Artefak>()
    private fun displayListTicket() {
        listTicket = myDb.daoListTicket().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        adapter =
            AdapterKeranjang(requireActivity(), listTicket, object : AdapterKeranjang.Listeners {
                override fun onUpdate() {
                    hitungTotal()
                }

                override fun onDelete(position: Int) {
                    listTicket.removeAt(position)
                    adapter.notifyDataSetChanged()
                    hitungTotal()
                }

            })
        rvListTicket.adapter = adapter
        rvListTicket.layoutManager = layoutManager
    }

    fun hitungTotal() {
        val listTicket = myDb.daoListTicket().getAll() as ArrayList

        var totalHarga = 0
        var isSelectedAll = true
        for (ticket in listTicket) {
            if (ticket.selected) {
                val harga = Integer.valueOf(ticket.harga)
                totalHarga += (harga * ticket.jumlah)
            } else {
                isSelectedAll = false
            }
        }

        cbAll.isChecked = isSelectedAll
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    fun mainButton() {
        btnDelete.setOnClickListener {

        }

        btnBayar.setOnClickListener {
            summaryTransaksi()
        }

        cbAll.setOnClickListener {
            for (i in listTicket.indices) {
                val ticket = listTicket[i]
                ticket.selected = cbAll.isChecked
                listTicket[i] = ticket
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun summaryTransaksi() {
        val listTicket = myDb.daoListTicket().getAll() as ArrayList

        for (ticket in listTicket) {
            if (ticket.selected) {
                listTicketTransaksi.add(ticket)
            }
        }

        if (listTicketTransaksi.size == 0) {
            Toast.makeText(requireActivity(), "Tidak ada tiket yang dipilih", Toast.LENGTH_SHORT)
                .show()
        } else if (tanggal == null) {
            Toast.makeText(requireActivity(), "Tanggal harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            sendTransaksi()
        }
    }

    private fun sendTransaksi() {
        val myTransaksi = NewTransaksi(
            user.id,
            listTicketTransaksi.size,
            user.name,
            user.phone,
            tanggal,
            listTicketTransaksi
        )
        ApiConfig.instanceRetrofit.checkout(
            /*user.id,
            listTicketTransaksi.size,
            user.name,
            user.phone,
            tanggal!!,
            listTicketTransaksi*/
            myTransaksi
        ).enqueue(object : Callback<TransaksiResponModel> {
            override fun onResponse(
                call: Call<TransaksiResponModel>,
                response: Response<TransaksiResponModel>
            ) {
                val respon = response.body()!!
                Toast.makeText(requireActivity(), respon.message, Toast.LENGTH_SHORT).show()
                if(respon.success == 1){
                    val intent = Intent(requireActivity(), DetailTransaksiActivity::class.java)
                        .putExtra(DetailTransaksiActivity.EXTRA_ID, respon.transaksi!!.id)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<TransaksiResponModel>, t: Throwable) {
                Toast.makeText(requireActivity(), "Gagal mengirim transaksi", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    lateinit var btnDelete: ImageView
    lateinit var rvListTicket: RecyclerView
    lateinit var tvTotal: TextView
    lateinit var btnBayar: TextView
    lateinit var cbAll: CheckBox
    private fun init(view: View) {
        btnDelete = view.findViewById(R.id.btn_delete)
        rvListTicket = view.findViewById(R.id.rv_produk)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
        cbAll = view.findViewById(R.id.cb_all)
    }

    override fun onResume() {
        displayListTicket()
        hitungTotal()
        super.onResume()
    }

    private fun startAndGetValueDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal Berkunjung")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(it)
            tanggal = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${
                calendar.get(
                    Calendar.DAY_OF_MONTH
                )
            }"
            /*binding.tvTanggal.setText(tanggal)*/
            tv_tanggal_berkunjung.text = tanggal
        }

        datePicker.show(requireActivity().supportFragmentManager, "Tag")
    }

}