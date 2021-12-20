package com.example.calcheck.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

import com.example.calcheck.R


class StepTrackerFragment : Fragment() , SensorEventListener {

    var myActivity: Activity? = null

    var stepText : TextView?=null
    var sensorManager : SensorManager?=null
    var running : Boolean = false
    var steps : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step_tracker, container, false)
        stepText = view.findViewById(R.id.lala)
        activity?.title = "Step Tracker"
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myActivity = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
        val SPString =sp1.getString("id",null)
        val sp = myActivity!!.getSharedPreferences(SPString, Context.MODE_PRIVATE)
        val i = sp.getInt("steps",-1)
        if(i==-1){
            val e : SharedPreferences.Editor = sp.edit()
            e.putInt("steps",steps)
            e.apply()
        }
        else{
            steps=i
        }

        stepText?.setText(steps.toString())
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onResume() {
        super.onResume()
        running  = true
        val stepTracker = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepTracker == null){
            Toast.makeText(myActivity as Context , "No Step Tracker Sensor in your Device!",Toast.LENGTH_LONG).show()
        }else{
            sensorManager?.registerListener(this , stepTracker , SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running){

            val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
            val SPString =sp1.getString("id",null)
            val sp = myActivity!!.getSharedPreferences(SPString, Context.MODE_PRIVATE)
            val e : SharedPreferences.Editor = sp.edit()
            e.putInt("steps",event!!.values[0].toInt())
            e.apply()

        }
    }


}
