package splashScreen

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import detailFetcher.LoginPage
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Display
import android.widget.Toast
import com.example.calcheck.MainActivity
import com.example.calcheck.R
import com.example.calcheck.databse.SqlHelper

import detailFetcher.Description
import detailFetcher.SignUp
import modelClass.ModelClass
import java.util.*


class Splash : AppCompatActivity() {

    var sql = SqlHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

     //   val id = LoginPage.Statified.id
       // val SPString = id.toString()
        //val sp = this.getSharedPreferences(SPString, Context.MODE_PRIVATE)
       // val loginBoolean = sp.getBoolean("loginBool" , false)


        val sp1 = this.getSharedPreferences(("Global"),Context.MODE_PRIVATE)
        val loginBoolean = sp1.getBoolean("Login",false)
        val DescriptionBoolean = sp1.getBoolean("DescriptionBool",false)

      //  val a = ModelClass(id=5 , username = "bina" , password = "bina")
        //sql.deleteUsers(a)

        if (loginBoolean == true && DescriptionBoolean == true) {
            Handler().postDelayed({
                val intent = Intent(this@Splash, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }, 2000)
        }
        else if(loginBoolean == true && DescriptionBoolean!=true){
            Handler().postDelayed({
                val intent = Intent(this@Splash, Description::class.java)
                startActivity(intent)
                this.finish()
            }, 2000)
        }
        else if(loginBoolean == false && DescriptionBoolean == false){
            Handler().postDelayed({
                val intent = Intent(this@Splash, LoginPage::class.java)
                startActivity(intent)
                this.finish()
            }, 2000)
        }
        else if(loginBoolean == false && DescriptionBoolean == true){
            Handler().postDelayed({
                val intent = Intent(this@Splash, LoginPage::class.java)
                startActivity(intent)
                this.finish()
            }, 2000)
        }
    }
}
