package com.kantin.e_ticket.model.transaksi


import com.google.gson.annotations.SerializedName

data class TransaksiResponModel(
    @SerializedName("success")
    val success: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("list_transaksi")
    val listTransaksi: List<Transaksi>? = null,
    @SerializedName("transaksi")
    val transaksi: Transaksi? = null
)