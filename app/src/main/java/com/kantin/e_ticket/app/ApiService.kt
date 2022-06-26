package com.kantin.e_ticket.app

import com.kantin.e_ticket.model.ResponModel
import com.kantin.e_ticket.model.transaksi.TransaksiResponModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded

    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ):Call<ResponModel>


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<ResponModel>


    @GET("ticket")
    fun getTicket():Call<ResponModel>

    @GET("transaksi/{user_id}")
    fun getTransaksi(
        @Path("user_id") user_id: Int
    ): Call<TransaksiResponModel>

    @GET("transaksi/detail/{transaksi_id}")
    fun getDetailTransaksi(
        @Path("transaksi_id") transaksi_id: Int
    ): Call<TransaksiResponModel>

    @Multipart
    @POST("transaksi/bukti-transfer")
    fun uploadBuktiTransfer(
        @Part("transaksi_id") transaksi_id: RequestBody,
        @Part("total_transfer") total_transfer: RequestBody,
        @Part bukti_transfer: MultipartBody.Part? = null
    ): Call<TransaksiResponModel>

}