package com.kantin.e_ticket.model.transaksi


import com.google.gson.annotations.SerializedName

data class Transaksi(
    @SerializedName("bank")
    val bank: String? = null,
    @SerializedName("bukti_transfer")
    val buktiTransfer: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("deskripsi")
    val deskripsi: String? = null,
    @SerializedName("detail_tanggal")
    val detailTanggal: String? = null,
    @SerializedName("expired_at")
    val expiredAt: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("kode_payment")
    val kodePayment: String,
    @SerializedName("kode_trx")
    val kodeTrx: String,
    @SerializedName("kode_unik")
    val kodeUnik: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("total_harga")
    val totalHarga: Int? = null,
    @SerializedName("total_item")
    val totalItem: Int,
    @SerializedName("total_transfer")
    val totalTransfer: Int,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("user_id")
    val userId: Int
)