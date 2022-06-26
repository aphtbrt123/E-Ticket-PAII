package com.kantin.e_ticket.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "listTicket")
public class Artefak implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    public int idTb;


    public int id;
    public String name;
    public String harga;
    public String deskripsi;
    public int category_id;
    public String created_at;
    public String updated_at;
    public String image;

    public int jumlah = 1;
    public boolean selected = true;

}
