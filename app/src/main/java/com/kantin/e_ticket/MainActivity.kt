package com.kantin.e_ticket

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kantin.e_ticket.activity.LoginActivity
import com.kantin.e_ticket.activity.MasukActivity
import com.kantin.e_ticket.fragment.AkunFragment
import com.kantin.e_ticket.fragment.HomeFragment
import com.kantin.e_ticket.fragment.TiketFragment
import com.kantin.e_ticket.helper.SharedPref

class MainActivity : AppCompatActivity() {

    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentTiket: Fragment = TiketFragment()
    private val fragmentAkun: Fragment = AkunFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentHome

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private var statusLogin = false

    private lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        s = SharedPref(this)

        setUpButtonNav()

    }

    fun setUpButtonNav(){
        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentTiket).hide(fragmentTiket).commit()
        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_home ->{
                    callFragment(0,fragmentHome, fragmentHome)
                }

                R.id.navigation_tiket ->{
                    callFragment(1,fragmentTiket, fragmentTiket)
                }

                R.id.navigation_akun ->{
                    if (s.getStatusLogin()){
                        callFragment(2, fragmentAkun, fragmentAkun)
                    }else{
                        startActivity(Intent(this, MasukActivity::class.java))
                    }

                }
            }
            false
        }
    }

    fun callFragment(int: Int, fragment: Fragment, fragment2: Fragment){
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment2
    }
}