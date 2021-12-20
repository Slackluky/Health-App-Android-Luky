package databaseFunctionalities

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.example.calcheck.databse.SqlHelper

class InputValidation(private val context: Context) {

    val sql = SqlHelper(context)

    fun isEmpty(userNameEditText : EditText, passwordEditText : EditText, editConfirm : EditText ,userNameError:TextView, passwordError:TextView ,confirmError: TextView ):Boolean{
        var userNameText = userNameEditText.text.toString().trim()
        var passwordText = passwordEditText.text.toString().trim()
        var confirmText =  editConfirm.text.toString().trim()
        var bool = false

        if(userNameText.isEmpty()){
            userNameError.setText("ENTER USERNAME")
            hideKeyboardFrom(userNameEditText)
            bool = true
        }
        if( passwordText.isEmpty()){
            passwordError.setText("ENTER PASSWORD")
            hideKeyboardFrom(passwordEditText)
            bool = true
        }
        if( confirmText.isEmpty()){
            confirmError.setText("ENTER CONFIRM FIELD")
            hideKeyboardFrom(editConfirm)
            bool = true
        }
        return bool
    }

    fun isEmpty(userNameEditText : EditText, passwordEditText : EditText, userNameError:TextView, passwordError:TextView):Boolean{
        var userNameText = userNameEditText.text.toString().trim()
        var passwordText = passwordEditText.text.toString().trim()

        var bool = false

        if(userNameText.isEmpty()){
            userNameError.setText("ENTER USERNAME")
            hideKeyboardFrom(userNameEditText)
            bool = true
        }
        if( passwordText.isEmpty()){
            passwordError.setText("ENTER PASSWORD")
            hideKeyboardFrom(passwordEditText)
            bool = true
        }
        return bool
    }

    fun checkConfirm(passwordEditText : EditText , confirmEditText : EditText , confirmError :TextView):Boolean{
        return if(passwordEditText.text.toString().trim() == confirmEditText.text.toString().trim()){

            false
        } else{
            confirmError.setText ( "PASSWORD DOESNT MATCH")
            hideKeyboardFrom(confirmEditText)
            true
        }
    }

    private fun hideKeyboardFrom(view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}