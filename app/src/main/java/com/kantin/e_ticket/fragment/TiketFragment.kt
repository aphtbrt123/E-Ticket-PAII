package com.kantin.e_ticket.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kantin.e_ticket.R
import com.kantin.e_ticket.adapter.AdapterKeranjang
import com.kantin.e_ticket.helper.Helper
import com.kantin.e_ticket.helper.SharedPref
import com.kantin.e_ticket.model.Artefak
import com.kantin.e_ticket.room.MyDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TiketFragment : Fragment() {
    // TODO: Rename and change types of parameters


    lateinit var myDb : MyDatabase
    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            myDb = MyDatabase.getInstance(requireActivity())!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_tiket, container, false)
        init(view)

        myDb = MyDatabase.getInstance(requireActivity())!!
        s = SharedPref(requireActivity())

        mainButton()

        return view
    }

    lateinit var adapter : AdapterKeranjang
    var listTicket = ArrayList<Artefak>()
    private fun displayListTicket(){
        listTicket = myDb.daoListTicket().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        adapter = AdapterKeranjang(requireActivity(), listTicket, object : AdapterKeranjang.Listeners{
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

    fun hitungTotal(){
        val listTicket = myDb.daoListTicket().getAll() as ArrayList

        var totalHarga = 0
        var isSelectedAll = true
        for (ticket in listTicket){
            if (ticket.selected){
                val harga = Integer.valueOf(ticket.harga)
                totalHarga += (harga * ticket.jumlah)
            }else{
                isSelectedAll = false
            }
        }

        cbAll.isChecked = isSelectedAll
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    fun mainButton(){
        btnDelete.setOnClickListener {

        }

        btnBayar.setOnClickListener {

        }

        cbAll.setOnClickListener {
            for (i in listTicket.indices){
                val ticket = listTicket[i]
                ticket.selected = cbAll.isChecked
                listTicket[i] = ticket
            }
            adapter.notifyDataSetChanged()
        }
    }

    lateinit var btnDelete :ImageView
    lateinit var rvListTicket :RecyclerView
    lateinit var tvTotal :TextView
    lateinit var btnBayar :TextView
    lateinit var cbAll : CheckBox
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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}