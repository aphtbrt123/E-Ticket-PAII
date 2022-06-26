package com.kantin.e_ticket.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.del.d3ti20.util.RealPathUtil
import com.inyongtisto.tokoonline.app.ApiConfig
import com.kantin.e_ticket.activity.DetailTransaksiActivity
import com.kantin.e_ticket.databinding.FragmentBuktiTransferTransaksiBinding
import com.kantin.e_ticket.model.transaksi.TransaksiResponModel
import com.kantin.e_ticket.util.Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class BuktiTransferTransaksiFragment : DialogFragment() {
    lateinit var binding: FragmentBuktiTransferTransaksiBinding

    var transaksiId: Int = 0

    private var data: Intent? = null
    private var uri: Uri? = null
    private var path: String? = null
    private var file: File? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                getFile()
            }
        }

    private val selectPhotoResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                data = result.data
                if (data != null) {
                    uri = data!!.data
                    result()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBuktiTransferTransaksiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    private fun setUp() {
        transaksiId = (activity as DetailTransaksiActivity).idTransaksi
        Log.d("MyTag", "id transaksi: $transaksiId")
        if (transaksiId == 0) {
            dismiss()
        }

        binding.editTextJumlahTransfer.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()) {
                binding.layoutBuktiTransfer.error = "Jumlah transfer tidak boleh kosong"
            } else {
                binding.layoutBuktiTransfer.error = null
            }
        }

        binding.buttonSelectBuktiTransfer.setOnClickListener {
            getFile()
        }

        binding.imageBuktiTransfer.setOnClickListener {
            getFile()
        }

        binding.buttonKirimBuktiTransfer.setOnClickListener {
            send()
        }
    }

    private fun getFile() {
        activity?.let {
            if (
                Helper.PERMISSIONS.all {
                    ActivityCompat.checkSelfPermission(
                        requireContext(),
                        it
                    ) == PackageManager.PERMISSION_GRANTED
                }
            ) {
                Log.d("MyTag", "Permission granted, and do something")
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        .setType("image/*")
                selectPhotoResultLauncher.launch(intent)
            } else {
                requestPermissionLauncher.launch(Helper.PERMISSIONS)
            }
        }
    }

    private fun result() {
        path = RealPathUtil.getRealPath(requireContext(), uri!!)
        file = File(path!!)

        binding.buttonSelectBuktiTransfer.visibility = View.GONE

        binding.imageBuktiTransfer.setImageURI(uri)
        binding.imageBuktiTransfer.visibility = View.VISIBLE
        binding.buttonKirimBuktiTransfer.visibility = View.VISIBLE
    }

    private fun send() {
        if (validateInput()) {
            binding.pb.visibility = View.VISIBLE

            ApiConfig.instanceRetrofit
                .uploadBuktiTransfer(
                    Helper.requestBody(transaksiId),
                    Helper.requestBody(binding.editTextJumlahTransfer.text.toString()),
                    Helper.requestBody(file)
                )
                .enqueue(object : Callback<TransaksiResponModel> {
                    override fun onResponse(
                        call: Call<TransaksiResponModel>,
                        response: Response<TransaksiResponModel>
                    ) {
                        val respon = response.body()!!

                        (activity as DetailTransaksiActivity).getTransaksi()
                        dismiss()
                    }

                    override fun onFailure(call: Call<TransaksiResponModel>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error " + t.message, Toast.LENGTH_LONG)
                            .show()
                    }
                })

            binding.pb.visibility = View.GONE
        }
    }

    private fun validateInput(): Boolean {
        var validation = false

        if (binding.editTextJumlahTransfer.text.toString().isEmpty()) {
            binding.layoutBuktiTransfer.error = "Jumlah transfer tidak boleh kosong"
            return validation
        }
        if (binding.editTextJumlahTransfer.error != null) {
            return validation
        }

        validation = true
        return validation
    }

    companion object {
        const val TAG = "BuktiTransferTransaksiFragment"
    }

}