package com.kantin.e_ticket.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.kantin.e_ticket.model.Artefak
import com.kantin.e_ticket.model.listTicket

@Dao
interface DaoListTicket {

    @Insert(onConflict = REPLACE)
    fun insert(data: Artefak)

    @Delete
    fun delete(data: Artefak)

    @Update
    fun update(data: Artefak): Int

    @Query("SELECT * from listTicket ORDER BY id ASC")
    fun getAll(): List<Artefak>

    @Query("SELECT * FROM listTicket WHERE id = :id LIMIT 1")
    fun getNote(id: Int): Artefak

    @Query("DELETE FROM listTicket")
    fun deleteAll(): Int
}