package com.kantin.e_ticket.helper

import java.text.NumberFormat
import java.util.*

class Helper {
    fun gantiRupiah(string: String): String{
        return NumberFormat.getCurrencyInstance(Locale("in","ID")).format(Integer.valueOf(string))
    }
}