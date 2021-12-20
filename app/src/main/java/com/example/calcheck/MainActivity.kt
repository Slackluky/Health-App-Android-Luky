package com.example.calcheck

import adapters.NavigationDrawerAdapter
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calcheck.broadcastreceiver.AlarmReceiver
import com.example.calcheck.fragments.MainScreenFrag
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var NavigationDrawerList : ArrayList<String> = arrayListOf()
    var IconList = intArrayOf(R.drawable.home , R.drawable.food , R.drawable.steptracker , R.drawable.settings , R.drawable.info)

    lateinit var pi : PendingIntent
    lateinit var am : AlarmManager

    object Statified {
        var drawerLayout: DrawerLayout? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        startAlarm()
        MainActivity.Statified.drawerLayout = findViewById(R.id.drawer_layout)

        NavigationDrawerList.add("Home")
        NavigationDrawerList.add("Track Intake")
        NavigationDrawerList.add("Step Tracker")
        NavigationDrawerList.add("Settings")
        NavigationDrawerList.add("About Us")

        val toggle = ActionBarDrawerToggle(this@MainActivity, Statified.drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        MainActivity.Statified.drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        val mainScreenFrag = MainScreenFrag()
        this.supportFragmentManager
            .beginTransaction()
            .add(R.id.detailsFragment , mainScreenFrag , "MainScreenFrag")
            .commit()

        val navigationAdapterObj = NavigationDrawerAdapter(NavigationDrawerList,IconList,this)
        navigationAdapterObj.notifyDataSetChanged()

        val navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter = navigationAdapterObj
        navigation_recycler_view.setHasFixedSize(true)
    }

    private fun startAlarm(){
        val calendar : Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.set(Calendar.HOUR_OF_DAY, 1)
        calendar.set(Calendar.MINUTE , 0)
        calendar.set(Calendar.SECOND , 0)

        if(Calendar.getInstance().after(calendar)){
            // Move to tomorrow
            calendar.add(Calendar.DATE, 1);
        }

        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(this,AlarmReceiver::class.java)
        pi = PendingIntent.getBroadcast(this.applicationContext , 0 , i , PendingIntent.FLAG_UPDATE_CURRENT)
        am.setRepeating(AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis(), 24*60*60*1000 , pi)

    }
}