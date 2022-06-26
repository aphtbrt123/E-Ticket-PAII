package com.kantin.e_ticket.model

import com.google.gson.annotations.SerializedName

class NewTransaksi(
    @SerializedName("user_id")
    var userId: Int? = null,

    @SerializedName("total_item")
    var totalItem: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("phone")
    var phone: String? = null,

    @SerializedName("detail_tanggal")
    var detailTanggal: String? = null,

    @SerializedName("tickets")
    var tickets: List<Artefak>? = null
) {

}