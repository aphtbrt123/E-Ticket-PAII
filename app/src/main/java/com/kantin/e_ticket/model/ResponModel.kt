package com.kantin.e_ticket.model

class ResponModel {
    var success = 0
    lateinit var message:String
    var user = User()
    var tickets:ArrayList<Artefak> = ArrayList()

}