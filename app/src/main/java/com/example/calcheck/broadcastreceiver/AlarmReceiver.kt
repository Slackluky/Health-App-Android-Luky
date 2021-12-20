package com.example.calcheck.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import java.util.*

public class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val sp1 : SharedPreferences = context!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
        val SPString =sp1.getString("id",null)
        val sp = context.getSharedPreferences(SPString,Context.MODE_PRIVATE)
        val e : SharedPreferences.Editor = sp.edit()

        e.putFloat("consumed",0f)
        e.putFloat("waterConsumed",0f)
        e.putInt("steps",0)
        val a : ArrayList<String> = ArrayList()
        val g = Gson()
        val j = g.toJson(a)
        e.putString("foodList",j)
        e.putString("quantityList",j)
        e.putString("calorieList",j)
        e.apply()
        e.commit()

        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)

        when (day) {
            Calendar.MONDAY -> {

            }
            Calendar.TUESDAY -> {
            }
            Calendar.WEDNESDAY -> {
            }
            Calendar.THURSDAY -> {
            }
            Calendar.FRIDAY -> {
            }
            Calendar.SATURDAY -> {
            }
            Calendar.SUNDAY -> {
            }
        }

    }

}
