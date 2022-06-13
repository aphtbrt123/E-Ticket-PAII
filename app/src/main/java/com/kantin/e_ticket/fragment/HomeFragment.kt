package com.kantin.e_ticket.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.inyongtisto.tutorial.adapter.AdapterSlider
import com.kantin.e_ticket.R
import com.kantin.e_ticket.adapter.AdapterArtefak
import com.kantin.e_ticket.model.Artefak

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var vpSlider:ViewPager
    lateinit var rvArtefak:RecyclerView
    lateinit var rvTiket:RecyclerView
    lateinit var rvKegiatan:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        vpSlider = view.findViewById(R.id.vp_slider)
        rvArtefak = view.findViewById(R.id.rv_artefak)
        rvTiket = view.findViewById(R.id.rv_tiket)
        rvKegiatan = view.findViewById(R.id.rv_kegiatan)

        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.g_perjuangan)
        arrSlider.add(R.drawable.gsm)
        arrSlider.add(R.drawable.g_ulos)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        //artefak
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        //tiket
        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        //kegiatan
        val layoutManager3 = LinearLayoutManager(activity)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        rvArtefak.adapter = AdapterArtefak(arrArtefak)
        rvArtefak.layoutManager = layoutManager

        rvTiket.adapter = AdapterArtefak(arrTiket)
        rvTiket.layoutManager = layoutManager2

        rvKegiatan.adapter = AdapterArtefak(arrKegiatan)
        rvKegiatan.layoutManager = layoutManager3





        return view
    }

    //recycler artefak home
    val arrArtefak: ArrayList<Artefak>get(){
        val arr = ArrayList<Artefak>()
        val A1 =  Artefak()
        A1.nama = "Artefak 1"
        A1.shortDescription = "Ini Artefak 1"
        A1.gambar = R.drawable.artefak1

        val A2 =  Artefak()
        A2.nama = "Artefak 2"
        A2.shortDescription = "Ini Artefak 2"
        A2.gambar = R.drawable.artefak2

        val A3 =  Artefak()
        A3.nama = "Artefak 3"
        A3.shortDescription = "Ini Artefak 3"
        A3.gambar = R.drawable.artefak3

        val A4 =  Artefak()
        A4.nama = "Artefak 4"
        A4.shortDescription = "Ini Artefak 4"
        A4.gambar = R.drawable.artefak4

        val A5 =  Artefak()
        A5.nama = "Artefak 5"
        A5.shortDescription = "Ini Artefak 5"
        A5.gambar = R.drawable.artefak5

        arr.add(A1)
        arr.add(A2)
        arr.add(A3)
        arr.add(A4)
        arr.add(A5)

        return arr
    }

    val arrTiket: ArrayList<Artefak>get(){
        val arr = ArrayList<Artefak>()
        val A1 =  Artefak()
        A1.nama = "Tiket 1"
        A1.shortDescription = "Ini Tiket 1"
        A1.gambar = R.drawable.artefak1

        val A2 =  Artefak()
        A2.nama = "Tiket 2"
        A2.shortDescription = "Ini Tiket 2"
        A2.gambar = R.drawable.artefak2

        val A3 =  Artefak()
        A3.nama = "Tiket 3"
        A3.shortDescription = "Ini Tiket 3"
        A3.gambar = R.drawable.artefak3

        val A4 =  Artefak()
        A4.nama = "Tiket 4"
        A4.shortDescription = "Ini Tiket 4"
        A4.gambar = R.drawable.artefak4

        val A5 =  Artefak()
        A5.nama = "Tiket 5"
        A5.shortDescription = "Ini Tiket 5"
        A5.gambar = R.drawable.artefak5

        arr.add(A1)
        arr.add(A2)
        arr.add(A3)
        arr.add(A4)
        arr.add(A5)

        return arr
    }

    val arrKegiatan: ArrayList<Artefak>get(){
        val arr = ArrayList<Artefak>()
        val A1 =  Artefak()
        A1.nama = "Kegiatan 1"
        A1.shortDescription = "Ini Kegiatan 1"
        A1.gambar = R.drawable.artefak1

        val A2 =  Artefak()
        A2.nama = "Kegiatan 2"
        A2.shortDescription = "Ini Kegiatan 2"
        A2.gambar = R.drawable.artefak2

        val A3 =  Artefak()
        A3.nama = "Kegiatan 3"
        A3.shortDescription = "Ini Kegiatan 3"
        A3.gambar = R.drawable.artefak3

        val A4 =  Artefak()
        A4.nama = "Kegiatan 4"
        A4.shortDescription = "Ini Kegiatan 4"
        A4.gambar = R.drawable.artefak4

        val A5 =  Artefak()
        A5.nama = "Kegiatan 5"
        A5.shortDescription = "Ini Kegiatan 5"
        A5.gambar = R.drawable.artefak5

        arr.add(A1)
        arr.add(A2)
        arr.add(A3)
        arr.add(A4)
        arr.add(A5)

        return arr
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