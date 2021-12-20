package detailFetcher

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.calcheck.R
import com.example.calcheck.databse.SqlHelper
import com.google.gson.Gson
import databaseFunctionalities.InputValidation
import detailFetcher.SignUp.Statified.userUnique
import modelClass.ModelClass

class SignUp : AppCompatActivity() , View.OnClickListener {

    object Statified{
        var signUpBool:Boolean ? = false
        var loginBool : Boolean?=false
        var userUnique : ArrayList<SharedPreferences> = ArrayList()
    }

    var id : Int = -1


    private lateinit var signinButton : Button
    private lateinit var loginButton : Button
    private lateinit var editConfirm : EditText
    private lateinit var editUSERNAME : EditText
    private lateinit var editPassword : EditText
    private lateinit var userNameError : TextView
    private lateinit var passwordError : TextView
    private lateinit var confirmError : TextView
    private lateinit var sql : SqlHelper
    private lateinit var inputVal :InputValidation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setViews()
        initObjects()
        initlisteners()

    }

    //initializing Views
    private fun setViews(){
        editPassword = findViewById(R.id.EditPASSWORD1)
        editUSERNAME = findViewById(R.id.EditUSERNAME1)
        editConfirm = findViewById(R.id.EditCONFIRM1)
        signinButton = findViewById(R.id.signinButton1)
        userNameError = findViewById(R.id.ErrorUSERNAME1)
        passwordError = findViewById(R.id.ErrorPASSWORD1)
        confirmError = findViewById(R.id.ErrorCONFIRM1)
        loginButton = findViewById(R.id.loginButton1)
    }

    //initializing objects
     private fun initObjects(){
         sql = SqlHelper(this@SignUp)
         inputVal = InputValidation(this@SignUp)
    }

    //initializing listeners
    private fun initlisteners(){
        signinButton.setOnClickListener(this)
        loginButton.setOnClickListener(this)
        }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginButton1 -> {val i = Intent(this@SignUp , LoginPage::class.java)
                startActivity(i)
            }
            R.id.signinButton1 -> postDataToSQL()
        }
    }

    private fun postDataToSQL(){
        if(inputVal.isEmpty(editUSERNAME,editPassword,editConfirm,userNameError,passwordError,confirmError)){
            Statified.signUpBool = false
            return
        }
        if(inputVal.checkConfirm(editPassword,editConfirm,confirmError)){
            Statified.signUpBool = false
            return
        }

        if(!sql.checkData(editUSERNAME.text.toString().trim())){
            val user = ModelClass(username = editUSERNAME.text.toString().trim() , password = editPassword.text.toString().trim())
            sql.addUsers(user)

            Toast.makeText(this , "Registration Successful" , Toast.LENGTH_SHORT).show()
            Statified.signUpBool = true


            val a = sql.getId(editUSERNAME.text.toString().trim())
            id=a

            val SPString = id.toString()
            val sp : SharedPreferences = this.getSharedPreferences(SPString , Context.MODE_PRIVATE)
            val foodList = ArrayList<String>()
            val quantityList = ArrayList<String>()
            val calorieList = ArrayList<String>()
            val editor :SharedPreferences.Editor = sp.edit()
            val gson = Gson()
            val foodJ = gson.toJson(foodList)
            val quantJ = gson.toJson(quantityList)
            val calorieJ = gson.toJson(calorieList)
            editor.putString("foodList",foodJ)
            editor.putString("quantityList",quantJ)
            editor.putString("calorieList",calorieJ)
            editor.apply()
            editor.commit()

            emptyInputEditText()
        }
        else{userNameError.setText("USERNAME IN USE")
            Statified.signUpBool = false
        }
    }

    private fun emptyInputEditText(){
        editUSERNAME.text=null
        editPassword.text=null
        editConfirm.text=null
    }
}
