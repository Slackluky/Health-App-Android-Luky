package com.example.calcheck.fragments

import XAxisValueFormatter
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.calcheck.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import detailFetcher.LoginPage
import kotlinx.android.synthetic.main.fragment_main_screen.*

class MainScreenFrag : Fragment() {

    lateinit var bmrChart :HorizontalBarChart
    lateinit var waterChart :HorizontalBarChart
    lateinit var foodButton : Button
    lateinit var waterButton : Button
    lateinit var numberByTotal : TextView
    lateinit var numberByTotalForWater : TextView
    lateinit var weekGraphFood : BarChart
    lateinit var weekGraphWater : BarChart

    var myActivity: Activity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)
        foodButton = view.findViewById(R.id.line)
        waterButton = view.findViewById(R.id.water)
        numberByTotal = view.findViewById(R.id.numberByTotalTextView)
        numberByTotalForWater = view.findViewById(R.id.numberByTotalTextViewForWater)
        activity?.title = "Home"
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

        setBMRGraph()
        setWaterGraph()
        setWeekGraphFood()
        setWeekGraphWater()

        val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
        val SPString =sp1.getString("id",null)
       // val id = LoginPage.Statified.id
        //val SPString = id.toString()
        var count = 0

        //Toast.makeText(myActivity , SPString , Toast.LENGTH_LONG).show()

        val sp : SharedPreferences = myActivity!!.getSharedPreferences(SPString,Context.MODE_PRIVATE)
        val consumed = sp.getFloat("consumed", 0.0F).toInt()
        val BMR = sp.getInt("BMR",1)
        val waterConsumed = sp.getFloat("waterConsumed",0.0f).toInt()
        numberByTotal.setText(consumed.toString() +  "/" + BMR.toString())
        numberByTotalForWater.setText(waterConsumed.toString() + "/" + 8)


        foodButton.setOnClickListener {
            waterButton.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            waterButton.setTextColor(resources.getColor(R.color.white))
            foodButton.setBackgroundColor(resources.getColor(R.color.white))
            foodButton.setTextColor(resources.getColor(R.color.colorPrimary))

            foodLayout.isVisible = true
            waterLayout.isVisible = false
        }

        waterButton.setOnClickListener {
            foodButton.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            foodButton.setTextColor(resources.getColor(R.color.white))
            waterButton.setBackgroundColor(resources.getColor(R.color.white))
            waterButton.setTextColor(resources.getColor(R.color.colorPrimary))

            foodLayout.isVisible = false
            waterLayout.isVisible = true

            if(count==0){
                waterChart.animateY(2000)
                weekGraphWater.animateY(2000)
                count += 1}
        }
    }

    private fun setBMRGraph(){

        bmrChart = BMRChart

        bmrChart.setDrawBarShadow(false)
        val description = Description()
        description.text=""
        bmrChart.description = description
        bmrChart.legend.isEnabled = false
        bmrChart.setDrawValueAboveBar(false)

        val x = bmrChart.getXAxis()
        x.setDrawGridLines(false)
        x.setPosition(XAxis.XAxisPosition.BOTTOM)
        x.setEnabled(true)
        x.setDrawAxisLine(false)

        val y = bmrChart.axisLeft
        y.axisMaximum = 4000f
        y.axisMinimum = 0f
        y.isEnabled = false

        x.setLabelCount(0)
        val values = arrayOf("")
        x.valueFormatter = XAxisValueFormatter(values)

        val yRight = bmrChart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        //Set bar entries and add necessary formatting
        setGraphData()

        //Add animation to the graph
        bmrChart.animateY(2000)
    }

    private fun setGraphData(){

        val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
        val SPString =sp1.getString("id",null)
        val sp = myActivity!!.getSharedPreferences(SPString, Context.MODE_PRIVATE)
        val CalConsumed : Float = sp.getFloat("consumed",0f)
        val BMR :Float = sp.getInt("BMR",1).toFloat()

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f,CalConsumed))

        val barDataSet = BarDataSet(entries, "Bar Data Set")

        if(CalConsumed<BMR){
        barDataSet.setColor(ContextCompat.getColor(bmrChart.context , R.color.graphGreen))}
        else{
            barDataSet.setColor(ContextCompat.getColor(bmrChart.context , R.color.graphRed))
        }

        bmrChart.setDrawBarShadow(true)
        barDataSet.barShadowColor = Color.argb(40,150,150,150)
        val data = BarData(barDataSet)
        data.barWidth = 0.9f

        bmrChart.data=data
        bmrChart.invalidate()

    }

    private fun setWaterGraph(){
        waterChart = WaterChart

        waterChart.setDrawBarShadow(false)
        val description = Description()
        description.text=""
        waterChart.description = description
        waterChart.legend.isEnabled = false
        waterChart.setDrawValueAboveBar(false)

        val x = waterChart.getXAxis()
        x.setDrawGridLines(false)
        x.setPosition(XAxis.XAxisPosition.BOTTOM)
        x.setEnabled(true)
        x.setDrawAxisLine(false)

        val y = waterChart.axisLeft
        y.axisMaximum = 8f
        y.axisMinimum = 0f
        y.isEnabled = false

        x.setLabelCount(0)
        val values = arrayOf("")
        x.valueFormatter = XAxisValueFormatter(values)

        val yRight = waterChart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        //Set bar entries and add necessary formatting
        setGraphDataWater()

        //Add animation to the graph
        //waterChart.animateY(2000)

    }

    private fun setGraphDataWater(){

        val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
        val SPString =sp1.getString("id",null)
        val sp = myActivity!!.getSharedPreferences(SPString, Context.MODE_PRIVATE)
        val WaterConsumed = sp.getFloat("waterConsumed" , 0.0f)

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f,WaterConsumed))

        val barDataSet = BarDataSet(entries, "Bar Data Set")

        barDataSet.setColors(ContextCompat.getColor(bmrChart.context , R.color.graphBlue))

        waterChart.setDrawBarShadow(true)
        barDataSet.barShadowColor = Color.argb(40,150,150,150)
        val data = BarData(barDataSet)
        data.barWidth = 0.9f

        waterChart.data=data
        waterChart.invalidate()
    }

    private fun setWeekGraphFood(){
        weekGraphFood = weekGraphFoodId
        weekGraphFood.setDrawBarShadow(false)
        val description = Description()
        description.text=""
        weekGraphFood.description = description
        weekGraphFood.legend.isEnabled=false

        val x = weekGraphFood.getXAxis()
        x.setDrawGridLines(false)
        x.setPosition(XAxis.XAxisPosition.BOTTOM)
        x.setEnabled(true)
        x.setDrawAxisLine(false)

        val y = weekGraphFood.axisLeft
        y.setDrawAxisLine(false)
        y.setDrawGridLines(false)
        y.axisMaximum = 4000f
        y.axisMinimum = 0f
        y.isEnabled = false

        val yRight = weekGraphFood.axisRight
        yRight.setDrawAxisLine(false)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        x.setLabelCount(7)
        val values = arrayOf("Mon","Tue","Wed","Thur","Fri","Sat","Sun")
        x.valueFormatter = XAxisValueFormatter(values)

        val CalX = ArrayList<BarEntry>() //y axis
        CalX.add(BarEntry(0f,2000f))
        CalX.add(BarEntry(1f,3000f))
        CalX.add(BarEntry(2f,1500f))
        CalX.add(BarEntry(3f,1000f))
        CalX.add(BarEntry(4f,2800f))
        CalX.add(BarEntry(5f,1800f))
        CalX.add(BarEntry(6f,2500f))

        val barDataSet = BarDataSet(CalX, "CalXLabel")

        barDataSet.setColors(
            ContextCompat.getColor(weekGraphFood.context, R.color.graphGreen),
            ContextCompat.getColor(weekGraphFood.context, R.color.graphRed),
            ContextCompat.getColor(weekGraphFood.context, R.color.graphGreen),
            ContextCompat.getColor(weekGraphFood.context, R.color.graphGreen),
            ContextCompat.getColor(weekGraphFood.context, R.color.graphRed),
            ContextCompat.getColor(weekGraphFood.context, R.color.graphGreen),
            ContextCompat.getColor(weekGraphFood.context, R.color.graphRed)
        )

        weekGraphFood.setDrawBarShadow(false)
        barDataSet.barShadowColor = Color.argb(40,150,150,150)
        val data = BarData(barDataSet)
        data.barWidth = 0.9f

        weekGraphFood.data=data
        weekGraphFood.invalidate()

        weekGraphFood.animateY(2000)
        
    }
    
    private fun setWeekGraphWater(){
        weekGraphWater = weekGraphWaterId
        weekGraphWater.setDrawBarShadow(false)
        val description = Description()
        description.text=""
        weekGraphWater.description = description
        weekGraphWater.legend.isEnabled=false

        val x = weekGraphWater.getXAxis()
        x.setDrawGridLines(false)
        x.setPosition(XAxis.XAxisPosition.BOTTOM)
        x.setEnabled(true)
        x.setDrawAxisLine(false)

        val y = weekGraphWater.axisLeft
        y.setDrawAxisLine(false)
        y.setDrawGridLines(false)
        y.axisMaximum = 10f
        y.axisMinimum = 0f
        y.isEnabled = false

        val yRight = weekGraphWater.axisRight
        yRight.setDrawAxisLine(false)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        x.setLabelCount(7)
        val values = arrayOf("Mon","Tue","Wed","Thur","Fri","Sat","Sun")
        x.valueFormatter = XAxisValueFormatter(values)

        val Water = ArrayList<BarEntry>() //y axis
        Water.add(BarEntry(0f,2f))
        Water.add(BarEntry(1f,3f))
        Water.add(BarEntry(2f,5f))
        Water.add(BarEntry(3f,1f))
        Water.add(BarEntry(4f,8f))
        Water.add(BarEntry(5f,9f))
        Water.add(BarEntry(6f,6f))



        val barDataSet = BarDataSet(Water, "CalXLabel")

        barDataSet.setColors(
            ContextCompat.getColor(weekGraphWater.context, R.color.graphBlue2),
            ContextCompat.getColor(weekGraphWater.context, R.color.graphBlue2),
            ContextCompat.getColor(weekGraphWater.context, R.color.graphBlue2),
            ContextCompat.getColor(weekGraphWater.context, R.color.graphBlue2),
            ContextCompat.getColor(weekGraphWater.context, R.color.graphBlue2),
            ContextCompat.getColor(weekGraphWater.context, R.color.graphBlue2),
            ContextCompat.getColor(weekGraphWater.context, R.color.graphBlue2)
        )

        weekGraphWater.setDrawBarShadow(false)
        barDataSet.barShadowColor = Color.argb(40,150,150,150)
        val data = BarData(barDataSet)
        data.barWidth = 0.9f


        weekGraphWater.data=data
        weekGraphWater.invalidate()

        //weekGraphWater.animateY(2000)
    }
}
