package com.kantin.e_ticket.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "listTicket") // the name of tabel
class listTicket {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null


    lateinit var id_ticket: String
    lateinit var name: String
    var harga: Int = 0
    lateinit var image: String
    var jumlah:Int = 1

}