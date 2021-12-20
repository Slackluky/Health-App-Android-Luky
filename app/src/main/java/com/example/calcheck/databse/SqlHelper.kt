package com.example.calcheck.databse

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import modelClass.ModelClass

class SqlHelper(context: Context?) : SQLiteOpenHelper(context,
    DB_Name, null,
    DB_Version
) {

    var userList : ArrayList<ModelClass>?=null
    companion object{
    val DB_Name = "Users"
    val Table_Name = "UsersTable"
    val Column_ID = "ID"
    val Column_UserName = "Username"
    val Column_Password = "Password"
    val DB_Version = 1;}

    private val tableCal = ("CREATE TABLE " + Table_Name + "(" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Column_UserName + " TEXT," + Column_Password + " TEXT" + ")")
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $Table_Name"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(tableCal)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_USER_TABLE)
        // Create tables again
        onCreate(db)
    }

    fun getAllUsers() : ArrayList<ModelClass>{
        val columns = arrayOf(
            Column_ID,
            Column_UserName,
            Column_Password
        )
        val userList = ArrayList<ModelClass>()

        val db = this.readableDatabase
        val cursor = db.query(Table_Name, columns , null , null, null ,null, null)

        if(cursor.moveToFirst()){
            do{
                val user = ModelClass( id = cursor.getString(cursor.getColumnIndex(
                    Column_ID
                )).toInt() , username = cursor.getString(cursor.getColumnIndex(
                    Column_UserName
                )) , password = cursor.getString(cursor.getColumnIndex(Column_Password)))
                userList.add(user)
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }


    fun addUsers(user : ModelClass) {

        val contentValues = ContentValues()
        contentValues.put(Column_UserName,user.username)
        contentValues.put(Column_Password, user.password)

        val db = this.writableDatabase
        db.insert(Table_Name, null , contentValues)
        db.close()

    }

    fun deleteUsers(user:ModelClass){
        val db = this.writableDatabase
        db.delete(Table_Name, "$Column_ID = ?" , arrayOf(user.id.toString()))
        db.close()
    }

    fun checkData(inputUsername:String):Boolean{
        val columns = arrayOf(Column_ID)
        val db = this.readableDatabase
        val selection = "$Column_UserName = ?"
        val selectionArgs = arrayOf(inputUsername)

        val cursor = db.query(
            Table_Name, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order

        val cursorcount = cursor.count
        cursor.close()
        db.close()

        return cursorcount>0
    }

    fun checkData(inputUsername:String , password:String):Boolean{
        val columns = arrayOf(Column_ID)
        val db = this.readableDatabase
        val selection = "$Column_UserName = ? AND $Column_Password = ?"
        val selectionArgs = arrayOf(inputUsername , password)

        val cursor = db.query(
            Table_Name, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order

        val cursorcount = cursor.count
        cursor.close()
        db.close()

        return cursorcount>0
    }

    fun getId(username : String) : Int{
        val Columns = arrayOf(Column_ID)
        val db = this.readableDatabase
        val selection = "$Column_UserName = ?"
        val SelectionArgs = arrayOf(username)

        val cursor = db.query(
            Table_Name, //Table to query
            Columns,        //columns to return
            selection,      //columns for the WHERE clause
            SelectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order

        cursor.moveToFirst()
        val num = cursor.getInt(0)
        val cursorcount = cursor.count
        cursor.close()
        db.close()

        return if(cursorcount>0){
            num
        } else{
            -1
        }
    }


}
