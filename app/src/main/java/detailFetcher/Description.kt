package detailFetcher

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import com.example.calcheck.MainActivity
import com.example.calcheck.R
import kotlinx.android.synthetic.main.activity_description.*
import splashScreen.Splash

class Description : AppCompatActivity() , View.OnClickListener{

    private lateinit var editAge : EditText
    private lateinit var errorAge : TextView
    private lateinit var editWeight : EditText
    private lateinit var SubmitButton :Button
    private lateinit var errorWeight: TextView
    private lateinit var errorGender : TextView
    private lateinit var errorHeight: TextView
    private lateinit var errorActivity : TextView
    private lateinit var textId : RelativeLayout
    private lateinit var textt: TextView
    private lateinit var aa : RelativeLayout

    var age :Int ? = null
    var weight : Int ? = null
    var gender : String ? = null
    var heightIn : Int ? = null
    var heightFt : Int ? = null
    var activity : String ? = null

    object Statified{
        var BMI : Double =0.0
        var BMR : Double = 0.0 }

    lateinit var context : Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        context = this
         setViews()
        setListeners()
        setSpinners()


    }

    private fun setViews(){
        editAge  = findViewById(R.id.EditAGE)
        errorAge = findViewById(R.id.AgeError)
        editWeight= findViewById(R.id.EditWeight)
        errorWeight = findViewById(R.id.WeightError)
        SubmitButton = findViewById(R.id.submitButton)
        errorGender = findViewById(R.id.GenderError)
        errorHeight = findViewById(R.id.HeightError)
        errorActivity = findViewById(R.id.ActivityError)
        textId = findViewById(R.id.textId)
        textt = findViewById(R.id.textt)
        aa = findViewById(R.id.aa)
    }

    fun setListeners(){
        SubmitButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.submitButton -> aaila()
        }
    }

    private fun setSpinners() {
        val genderArray = resources.getStringArray(R.array.Gender)
        val heightArrayFt = resources.getStringArray(R.array.Height_Feet)
        val heightArrayIn = resources.getStringArray(R.array.Height_Inches)
        val ActivityArray = resources.getStringArray(R.array.Active)

        // GENDER

        val spinnerGender = findViewById<Spinner>(R.id.GenderSpinner)
        if (spinnerGender != null) {
            val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderArray)
            spinnerGender.adapter = adapter1
            spinnerGender.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent : AdapterView<*> , view:View , position:Int , id:Long){
                        gender = genderArray[position]
                        if(gender!="none"){errorGender.setText("")}
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }

        //HEIGHT FEET

        val spinnerHeightFt = findViewById<Spinner>(R.id.HeightSpinnerFt)
        if(spinnerHeightFt!=null){
            val adapter2 = ArrayAdapter(this , android.R.layout.simple_spinner_item , heightArrayFt)
            spinnerHeightFt.adapter = adapter2

            spinnerHeightFt.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    heightFt = heightArrayFt[position].toInt()
                    if(heightFt!=0){errorHeight.setText("")}
                }
            }
        }

        // HEIGHT INCHES

        val spinnerHeightIn = findViewById<Spinner>(R.id.HeightSpinnerIn)
        if(spinnerHeightIn!=null){
            val adapter3 = ArrayAdapter(this , android.R.layout.simple_spinner_item , heightArrayIn)
            spinnerHeightIn.adapter = adapter3

            spinnerHeightIn.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    heightIn = heightArrayIn[position].toInt()
                    if(heightIn!=0){errorHeight.setText("")}
                }
            }
        }

        // ACTIVITY

        val spinnerActivity = findViewById<Spinner>(R.id.ActivitySpinner)
        if (spinnerActivity != null) {
            val adapter4 = ArrayAdapter(this, android.R.layout.simple_spinner_item, ActivityArray)
            spinnerActivity.adapter = adapter4
            spinnerActivity.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent : AdapterView<*> , view:View , position:Int , id:Long){
                    activity = ActivityArray[position]
                    if(activity!="none"){errorActivity.setText("")}
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }

    }

    fun aaila(){

        var b1 : Boolean?=null
        var b2 : Boolean?=null
        var b3 : Boolean?=null
        var b4 : Boolean?=null
        var b5 : Boolean?=null
        var b6 : Boolean?=null

        if(editAge.text.toString().isEmpty()){
            AgeError?.setText("Masukan Umur!")
            b1 = false;
            //Statified.descriptionBool = false
        }
        else{
            age = editAge.text.toString().toInt()
            //Statified.descriptionBool = false
            b1 =true
        }

        if(editWeight.text.toString().isEmpty()){
            WeightError?.setText("Masukan Berat Badan!")
            b2 = false
            //Statified.descriptionBool = false
        }
        else{
            weight = editWeight.text.toString().toInt()
            b2 = true
            //Statified.descriptionBool = false
        }

        if(gender == "none"){
            errorGender.setText("Masukan Jenis Kelamin!")
            b3 = false
            //Statified.descriptionBool = false
        }
        else{b3 = true}

        if(heightFt == 0){
            errorHeight.setText("Masukan Tinggi badan anda!")
            b4 = false
            //Statified.descriptionBool = false
        }
        else{b4 = true}

        if(heightIn == 0){
            errorHeight.setText("Masukkan Tinggi Badan!")
            b5 = false
            //Statified.descriptionBool = false
        }
        else{b5 = true}

        if(activity == "none"){
            errorActivity.setText("Masukan Jumlah Aktivitas!")
            b6 = false
            //Statified.descriptionBool = false
        }
        else{
            b6 = true
        }

        if(b1==true && b2==true && b3==true && b4==true && b5==true && b6==true){
            val heightftef = (heightFt)?.times(12)
            val heightef1 = heightIn?.plus(heightftef!!)
            val weightef = weight?.times(2.20462)

            val a  =  66.0 + (weightef?.toFloat()?.times(6.2)!!) + (heightef1?.toFloat()?.times(12.7)!!) - (age?.toFloat()?.times(6.76)!!)
            val b =  655.1 + (weightef?.toFloat()?.times(4.35)!!) + (heightef1?.toFloat()?.times(4.7)!!) - (age?.toFloat()?.times(4.7)!!)


            if (gender == "Male"){
                if(activity == "Not Active"){
                    Statified.BMR = a.times(1.2)
                }
                if(activity == "Slightly Active"){
                    Statified.BMR = a.times(1.37)
                }
                if(activity == "Moderately Active"){
                    Statified.BMR = a.times(1.55)
                }
                if(activity == "Very Active"){
                    Statified.BMR = a.times(1.725)
                }
            }
            if (gender == "Female"){
                if(activity == "Not Active"){
                    Statified.BMR = b.times(1.2)
                }
                if(activity == "Slightly Active"){
                    Statified.BMR = b.times(1.37)
                }
                if(activity == "Moderately Active"){
                    Statified.BMR = b.times(1.55)
                }
                if(activity == "Very Active"){
                    Statified.BMR = b.times(1.725)
                }
            }
            if (gender == "Other"){
                if(activity == "Not Active"){
                    Statified.BMR = (a.times(1.2) + b.times(1.2))/2
                }
                if(activity == "Slightly Active"){
                    Statified.BMR = (a.times(1.37) + b.times(1.37))/2
                }
                if(activity == "Moderately Active"){
                    Statified.BMR = (a.times(1.55) + b.times(1.55))/2
                }
                if(activity == "Very Active"){
                    Statified.BMR = (a.times(1.725) + b.times(1.725))/2
                }
            }

            Statified.BMI = (weightef.toDouble().times(703) / heightef1.toDouble().times(heightef1.toDouble()))

            //Statified.descriptionBool = true
            val show = Statified.BMR.toInt()
            val show2 = Statified.BMI.toInt()

            val sp1 : SharedPreferences = this.getSharedPreferences("Global",Context.MODE_PRIVATE)
            val SPString =sp1.getString("id",null)
            val sp = this.getSharedPreferences(SPString, Context.MODE_PRIVATE)
            val editor1 : SharedPreferences.Editor = sp.edit()
            editor1.putInt("BMR",show)
            editor1.putInt("BMI",show2)
            editor1.putBoolean("DescriptionBool",true)
            editor1.apply()
            editor1.commit()

           /* val copyBMR = sp.getInt("BMR",0)
            val copyBMI = sp.getInt("BMI",0)
           // val copyDesBool = sp.getBoolean("DescriptionBool",true)

            val sp1 : SharedPreferences = this.getSharedPreferences("Global",Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sp1.edit()
            //editor2.putBoolean("Login",true)
            editor.putInt("BMR",copyBMR)
            editor.putInt("BMI",copyBMI)
            editor.apply()
            editor.commit()*/

            aa.visibility = View.INVISIBLE
            textt.setText("You can consume " + show + " Calories each day to remain fit!")

            textId.visibility = View.VISIBLE

            Handler().postDelayed({
                val i = Intent(this@Description , MainActivity::class.java)
                startActivity(i)
            },2000)

        }
    }
}
