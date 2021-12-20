package com.example.calcheck.fragments

import XAxisValueFormatter
import adapters.FoodAdapter
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calcheck.DatabaseAccess
import com.example.calcheck.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_main_screen.foodLayout
import kotlinx.android.synthetic.main.fragment_main_screen.waterLayout
import kotlinx.android.synthetic.main.fragment_track_intake.*
import kotlinx.android.synthetic.main.fragment_track_intake.view.*


class TrackIntakeFragment : Fragment() {
    var myActivity: Activity? = null
    lateinit var foodButton : Button
    lateinit var waterButton : Button
    lateinit var waterTrackChart : BarChart
    lateinit var plus : ImageButton
    lateinit var minus : ImageButton
    var consumed = 0f
    var waterConsumed = 0f
    lateinit var textWaterTrack : TextView
    lateinit var quantitySpinner : Spinner
    lateinit var searchView : SearchView
    lateinit var adapter : ArrayAdapter<String>
    lateinit var listView : ListView
    lateinit var quantityText : TextView
    var list : ArrayList<String> = ArrayList<String>()
    var FoodListRecyclerView: RecyclerView?=null
    lateinit var previewText:TextView
    lateinit var add : Button
    lateinit var foodAdapterObj : FoodAdapter
    lateinit var swipeText : TextView

    var quantityValue : Int = 0
    var ItemSelected : String = ""
    var CalorieValue : Int = 0

    var foodList:ArrayList<String> = arrayListOf<String>()
    var quantityList:ArrayList<String> = arrayListOf<String>()
    var calorieList:ArrayList<String> = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_track_intake, container, false)
        foodButton = view.findViewById(R.id.line)
        waterButton = view.findViewById(R.id.water)
        plus = view.findViewById(R.id.plusButton)
        minus = view.findViewById(R.id.minusButton)
        textWaterTrack = view.findViewById(R.id.textWaterTrack)
        quantitySpinner = view.findViewById(R.id.quantitySpinner)
        quantityText = view.findViewById(R.id.quantityText)
        searchView = view.findViewById(R.id.searchID)
        listView = view.findViewById(R.id.lv1)
        FoodListRecyclerView = view.findViewById(R.id.FoodListRecyclerView)
        previewText = view.findViewById(R.id.PreviewText)
        swipeText = view.findViewById(R.id.text)
        add = view.findViewById(R.id.addButton)
        activity?.title = "Track Intake"
        setSpinner()
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



        val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
        val SPString =sp1.getString("id",null)
        val sp = myActivity!!.getSharedPreferences(SPString,Context.MODE_PRIVATE)

      /*  var a : ArrayList<String> = ArrayList()
        val g = Gson()
        val j = g.toJson(a)
        val e : SharedPreferences.Editor = sp.edit()
        e.putString("foodList",j)
        e.putString("quantityList",j)
        e.putString("calorieList",j)
        e.putFloat("consumed",0f)
        e.apply()
        e.commit()*/

        val gson = Gson()
        var foodJ = sp.getString("foodList",null)
        var quantJ =  sp.getString("quantityList",null)
        var calorieJ  = sp.getString("calorieList",null)

        if(foodJ==null || quantJ==null||calorieJ==null){
            val a : ArrayList<String> = ArrayList()
            val g = Gson()
            val j = g.toJson(a)
            val e : SharedPreferences.Editor = sp.edit()
            e.putString("foodList",j)
            e.putString("quantityList",j)
            e.putString("calorieList",j)
            e.putFloat("consumed",0f)
            e.apply()

            foodJ = sp.getString("foodList",null)
            quantJ =  sp.getString("quantityList",null)
            calorieJ  = sp.getString("calorieList",null)
        }

        val type = object : TypeToken<ArrayList<String>>() {}.type

        foodList = gson.fromJson(foodJ , type)
        quantityList = gson.fromJson(quantJ,type)
        calorieList = gson.fromJson(calorieJ,type)

        if(foodList.isNotEmpty()){
            swipeText.isVisible = true
        }

        foodAdapterObj = FoodAdapter(myActivity as Context , foodList as ArrayList<String>,quantityList as ArrayList<String>,calorieList as ArrayList<String>)
        foodAdapterObj.notifyDataSetChanged()

        FoodListRecyclerView?.layoutManager = LinearLayoutManager(myActivity as Context)
        FoodListRecyclerView?.itemAnimator = DefaultItemAnimator()
        ItemTouchHelper(itemTouchHelperR).attachToRecyclerView(FoodListRecyclerView)
        ItemTouchHelper(itemTouchHelperL).attachToRecyclerView(FoodListRecyclerView)
        FoodListRecyclerView?.adapter = foodAdapterObj
        FoodListRecyclerView?.setHasFixedSize(false)

        setWaterTrackChart()
        val d : DatabaseAccess = DatabaseAccess.getInstance(myActivity)
        d.open()
        list = d.getFood()
        d.close()

        println(foodList)
        println(quantityList)
        println(calorieList)

        adapter = ArrayAdapter(myActivity!!, android.R.layout.simple_list_item_1, list)
        listView.adapter=adapter

        searchView.setOnSearchClickListener { listView.isVisible=true; swipeText.isVisible=false; quantitySpinner.isVisible = false; quantityText.isVisible = false ; FoodListRecyclerView?.isVisible=false; previewText.isVisible=false; add.isVisible=false}
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var a = listClick()
            override fun onQueryTextSubmit(query: String): Boolean {
                listView.isVisible = true
                adapter.getFilter().filter(query)
                // searchView.setQuery(a,true)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                listView.isVisible = true
                adapter.getFilter().filter(newText)
                //searchView.setQuery(a,true)
                return false
            }
        })
        searchView.setOnCloseListener {   listView.isVisible = false
            adapter.getFilter().filter("")
            //Toast.makeText(this,"works",Toast.LENGTH_LONG).show()
            searchView.setIconifiedByDefault(true);
            quantitySpinner.isVisible = true
            FoodListRecyclerView?.isVisible = true
            quantityText.isVisible = true
            previewText.isVisible = true
            add.isVisible = true
            swipeText.isVisible=true
            false }


        waterConsumed = sp.getFloat("waterConsumed",0f)
        consumed = sp.getFloat("consumed",0f)

        add.setOnClickListener {

            if (quantityValue != 0 && CalorieValue != 0 && ItemSelected != "") {

                if(foodList.contains(ItemSelected)){
                    val i = foodList.indexOf(ItemSelected)
                    var p = quantityList.get(i).toInt()
                    p = p + quantityValue
                    quantityList.set(i , p.toString())

                    foodAdapterObj = FoodAdapter(
                        myActivity as Context,
                        foodList ,
                        quantityList,
                        calorieList)
                    foodAdapterObj.notifyDataSetChanged()

                    swipeText.isVisible = true
                }
                else {
                    foodList.add(ItemSelected.toString())
                    quantityList.add(quantityValue.toString())
                    calorieList.add(CalorieValue.toString())

                    foodAdapterObj = FoodAdapter(
                        myActivity as Context,
                        foodList,
                        quantityList,
                        calorieList
                    )
                    foodAdapterObj.notifyDataSetChanged()

                    swipeText.isVisible = true
                }

                FoodListRecyclerView?.layoutManager = LinearLayoutManager(myActivity as Context)
                FoodListRecyclerView?.itemAnimator = DefaultItemAnimator()
                ItemTouchHelper(itemTouchHelperR).attachToRecyclerView(FoodListRecyclerView)
                ItemTouchHelper(itemTouchHelperL).attachToRecyclerView(FoodListRecyclerView)
                FoodListRecyclerView?.adapter = foodAdapterObj
                FoodListRecyclerView?.setHasFixedSize(false)

                consumed = consumed + (CalorieValue.toFloat() * quantityValue)

                val editor: SharedPreferences.Editor = sp.edit()
                editor.putFloat("consumed", consumed)
                val gg = Gson()
                val fJ = gg.toJson(foodList)
                val qJ = gg.toJson(quantityList)
                val cJ = gg.toJson(calorieList)
                editor.putString("foodList",fJ)
                editor.putString("quantityList",qJ)
                editor.putString("calorieList",cJ)
                editor.apply()
            }
        }

        textWaterTrack.setText(waterConsumed.toInt().toString())

        plus.setOnClickListener{
            waterConsumed += 1
            val editor :SharedPreferences.Editor = sp.edit()
           // editor.putFloat("consumed" , consumed)
            editor.putFloat("waterConsumed" , waterConsumed)
            editor.apply()

            textWaterTrack.setText(waterConsumed.toInt().toString())


            if(waterConsumed<9f) {
                val a = sp.getFloat("waterConsumed", 0f)

                val Water = ArrayList<BarEntry>() //y axis
                Water.add(BarEntry(0f, a))
                val barDataSet = BarDataSet(Water, "waterTrack")

                barDataSet.setColors(
                    ContextCompat.getColor(waterTrackChart.context, R.color.graphBlue2)
                )
                waterTrackChart.setDrawBarShadow(false)
                barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
                val data = BarData(barDataSet)
                data.barWidth = 2f

                waterTrackChart.data = data
                //waterTrackChart.animateY(1000)
                waterTrackChart.notifyDataSetChanged()
                waterTrackChart.invalidate()
            }
        }

        minus.setOnClickListener{
            if(waterConsumed>0) {
                waterConsumed -= 1
                val editor :SharedPreferences.Editor = sp.edit()
                // editor.putFloat("consumed" , consumed)
                editor.putFloat("waterConsumed" , waterConsumed)
                editor.apply()
                editor.commit()
                textWaterTrack.setText(waterConsumed.toInt().toString())

                if(waterConsumed<8){
                    val a = sp.getFloat("waterConsumed", 0f)

                    val Water = ArrayList<BarEntry>() //y axis
                    Water.add(BarEntry(0f, a))
                    val barDataSet = BarDataSet(Water, "waterTrack")

                    barDataSet.setColors(
                        ContextCompat.getColor(waterTrackChart.context, R.color.graphBlue2)
                    )

                    waterTrackChart.setDrawBarShadow(false)
                    barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
                    val data = BarData(barDataSet)
                    data.barWidth = 2f

                    waterTrackChart.data = data
                    //waterTrackChart.animateY(1000)
                    waterTrackChart.notifyDataSetChanged()
                    waterTrackChart.invalidate()
                }
            }
        }

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
        }
    }

    val itemTouchHelperR = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean { return false}

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val i = viewHolder.adapterPosition
            val q = quantityList.get(i).toInt()
            val c = calorieList.get(i).toInt()
            foodList.removeAt(i)
            quantityList.removeAt(i)
            calorieList.removeAt(i)

            if(foodList.isEmpty()){
                swipeText.isVisible = false
            }

            foodAdapterObj.notifyDataSetChanged()

            consumed = consumed - (q*c)

            val g = Gson()
            val F = g.toJson(foodList)
            val Q = g.toJson(quantityList)
            val C = g.toJson(calorieList)

            val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
            val SPString =sp1.getString("id",null)
            val sp = myActivity!!.getSharedPreferences(SPString,Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sp.edit()
            editor.putFloat("consumed", consumed)
            editor.putString("foodList",F)
            editor.putString("quantityList",Q)
            editor.putString("calorieList",C)
            editor.apply()
        }
    }
    val itemTouchHelperL = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val i = viewHolder.adapterPosition
            val q = quantityList.get(i).toInt()
            val c = calorieList.get(i).toInt()
            foodList.removeAt(i)
            quantityList.removeAt(i)
            calorieList.removeAt(i)

            foodAdapterObj.notifyDataSetChanged()

            consumed = consumed - (q*c)

            val g = Gson()
            val F = g.toJson(foodList)
            val Q = g.toJson(quantityList)
            val C = g.toJson(calorieList)

            val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
            val SPString =sp1.getString("id",null)
            val sp = myActivity!!.getSharedPreferences(SPString,Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sp.edit()
            editor.putFloat("consumed", consumed)
            editor.putString("foodList",F)
            editor.putString("quantityList",Q)
            editor.putString("calorieList",C)
            editor.apply()
        }

    }
    private fun listClick():String{
        var a : String = "null"
        listView.setOnItemClickListener{ parent , view , position , id ->
            a = adapter.getItem(position)!!
            searchView.isIconified = true
            listView.isVisible =false
            searchView.isIconified=true
            //searchView.onActionViewCollapsed()
            ItemSelected = a
            quantitySpinner.isVisible = true
            quantityText.isVisible = true
            FoodListRecyclerView?.isVisible = true
            previewText.isVisible = true
            add.isVisible=true
            swipeText.isVisible=true

            val d : DatabaseAccess = DatabaseAccess.getInstance(myActivity as Context)
            d.open()
            val cal = d.getCal(a)
            d.close()

            CalorieValue = cal
            if(quantityValue!=0){
            previewText.setText(a + "  x  " + quantityValue.toString())}
            else{
                previewText.setText(a + "  x  ()")
            }
        }
        return a
    }

    private fun setSpinner(){
        val quantityArray = resources.getStringArray(R.array.quantity)
        val adapter = ArrayAdapter<String>(myActivity!! ,R.layout.spinner_item, quantityArray)
        quantitySpinner.adapter = adapter
        quantitySpinner.onItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                quantityValue = quantityArray[position].toInt()
                if(quantityValue!=0 && ItemSelected!=""){
                previewText.setText(ItemSelected + "  x  " + quantityValue.toString())}
                else{
                    previewText.setText("Preview")
                }
            }
        }
    }

    private fun setWaterTrackChart(){
        waterTrackChart = waterTrackGraphId
        waterTrackChart.setDrawBarShadow(false)
        val description = Description()
        description.text=""
        waterTrackChart.description = description
        waterTrackChart.legend.isEnabled=false

        val x =waterTrackChart.getXAxis()
        x.setDrawGridLines(false)
        x.setPosition(XAxis.XAxisPosition.BOTTOM)
        x.setEnabled(true)
        x.setDrawAxisLine(false)

        val y = waterTrackChart.axisLeft
        y.setDrawAxisLine(false)
        y.setDrawGridLines(false)
        y.axisMaximum = 8f
        y.axisMinimum = 0f
        y.isEnabled = false

        val yRight = waterTrackChart.axisRight
        yRight.setDrawAxisLine(false)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        x.setLabelCount(0)
        val values = arrayOf("")
        x.valueFormatter = XAxisValueFormatter(values)

        val sp1 : SharedPreferences = myActivity!!.getSharedPreferences("Global",Context.MODE_PRIVATE)
        val SPString =sp1.getString("id",null)
        val sp = myActivity!!.getSharedPreferences(SPString,Context.MODE_PRIVATE)
        val a = sp.getFloat("waterConsumed",0f)

        val Water = ArrayList<BarEntry>() //y axis
        Water.add(BarEntry(0f,a))
        val barDataSet = BarDataSet(Water, "waterTrack")

        barDataSet.setColors(
            ContextCompat.getColor(waterTrackChart.context, R.color.graphBlue2)
        )

        waterTrackChart.setDrawBarShadow(false)
        barDataSet.barShadowColor = Color.argb(40,150,150,150)
        val data = BarData(barDataSet)
        data.barWidth = 2f

        waterTrackChart.data=data
       // waterTrackChart.animateY(2000)
        waterTrackChart.notifyDataSetChanged()
        waterTrackChart.invalidate()
    }

}
