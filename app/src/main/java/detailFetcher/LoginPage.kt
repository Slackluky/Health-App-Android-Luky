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
import com.example.calcheck.MainActivity
import com.example.calcheck.R
import com.example.calcheck.databse.SqlHelper
import databaseFunctionalities.InputValidation
import detailFetcher.LoginPage.Statified.id
import detailFetcher.SignUp.Statified.loginBool
import splashScreen.Splash

class LoginPage : AppCompatActivity() , View.OnClickListener{

    private lateinit var signinButton : Button
    private lateinit var loginButton : Button
    private lateinit var editUSERNAME : EditText
    private lateinit var editPassword : EditText
    private lateinit var userNameError : TextView
    private lateinit var passwordError : TextView
    private lateinit var sql : SqlHelper
    private lateinit var inputVal : InputValidation

    object Statified{
        var id = -1
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        setViews()
        initObjects()
        initlisteners()
    }

    private fun setViews(){
        editPassword = findViewById(R.id.EditPASSWORD)
        editUSERNAME = findViewById(R.id.EditUSERNAME)
        loginButton = findViewById(R.id.loginButton)
        signinButton = findViewById(R.id.signinButton)
        userNameError = findViewById(R.id.ErrorUSERNAME)
        passwordError = findViewById(R.id.ErrorPASSWORD)
    }

    private fun initObjects(){
        sql = SqlHelper(this@LoginPage)
        inputVal = InputValidation(this@LoginPage)
    }

    private fun initlisteners(){
        signinButton.setOnClickListener(this)
        loginButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginButton -> checkWithSQL()
            R.id.signinButton -> {val i = Intent(this@LoginPage , SignUp::class.java)
                startActivity(i)
            }
        }
    }

    private fun checkWithSQL(){
        if(inputVal.isEmpty(editUSERNAME , editPassword , userNameError , passwordError)){
            SignUp.Statified.loginBool = false
            return
        }

       if(sql.checkData(editUSERNAME.text.toString().trim(), editPassword.text.toString().trim())){

           id = sql.getId(editUSERNAME.text.toString().trim())
           val SPString = id.toString()
           val sp: SharedPreferences = this.getSharedPreferences(SPString,Context.MODE_PRIVATE)
           val editor : SharedPreferences.Editor = sp.edit()
           editor.putBoolean("loginBool",true)
           //editor.putStrin = sp.Set(userUnique.toSet().toString())
           editor.apply()
           editor.commit()

          // val copy = sp.getBoolean("loginBool",true)

           val sp1 : SharedPreferences = this.getSharedPreferences("Global",Context.MODE_PRIVATE)
           val editor1 : SharedPreferences.Editor = sp1.edit()
           editor1.putBoolean("Login",true)
           editor1.putString("id",SPString)
           editor1.apply()
           editor1.commit()

           // Splash.Statified.loginBoolean = sharedPreferences.getBoolean("loginBool",false)
           //val ida = sql.getId(editUSERNAME.text.toString().trim())
           //Toast.makeText(this , ida.toString() , Toast.LENGTH_SHORT).show()

           val b = sp.getBoolean("DescriptionBool", false)

           if(!b){
            val ii = Intent(this@LoginPage , Description::class.java)
            emptyInputEditText()
            startActivity(ii)}
           else{
                val ii = Intent(this@LoginPage , MainActivity::class.java)
                emptyInputEditText()
                startActivity(ii)
            }

        }

        else{
           SignUp.Statified.loginBool = false
            Toast.makeText(this@LoginPage , "NO ACCOUNT FOUND" , Toast.LENGTH_LONG).show()
        }

    }
    private fun emptyInputEditText(){
        editUSERNAME.text=null
        editPassword.text=null
    }

}
