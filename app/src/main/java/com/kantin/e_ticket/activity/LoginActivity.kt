package com.kantin.e_ticket.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.inyongtisto.tokoonline.app.ApiConfig
import com.kantin.e_ticket.MainActivity
import com.kantin.e_ticket.R
import com.kantin.e_ticket.helper.SharedPref
import com.kantin.e_ticket.model.ResponModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)

        btn_login.setOnClickListener {
            login()
        }
    }

    fun login() {
        if (edt_email.text.isEmpty()) {
            edt_email.error = "Kolom Email tidak boleh kosong"
            edt_email.requestFocus()
            return
        } else if (edt_password.text.isEmpty()) {
            edt_password.error = "Kolom Password tidak boleh kosong"
            edt_password.requestFocus()
            return
        }


        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.login(edt_email.text.toString(), edt_password.text.toString()).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                pb.visibility = View.GONE
                val respon = response.body()!!

                if (respon.success == 1) {
                    s.setStatusLogin(true)
                    s.setUser(respon.user)
//                    s.setString(s.nama,respon.user.name)
//                    s.setString(s.phone,respon.user.phone)
//                    s.setString(s.email,respon.user.email)
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                    Toast.makeText(
                        this@LoginActivity,
                        "Selamat datang " + respon.user.name,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    //gagal
                    Toast.makeText(
                        this@LoginActivity,
                        "Error" + respon.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error" + t.message, Toast.LENGTH_LONG).show()
            }

        })


    }
}