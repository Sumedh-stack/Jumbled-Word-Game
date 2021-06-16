package com.wordsforfun.words


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQHELperveg(context: Context): SQLiteOpenHelper(context,DB_name,null,1)
{


    companion object{
        val DB_name="subjectssss.db "
        val TB_name="Subjectsss "
        val id="IDsss"
        val title ="S_titlesss"
        val desc="S_descsss"




    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TB_name(IDsss INTEGER PRIMARY KEY AUTOINCREMENT,S_titlesss TEXT,S_descsss TEXT)")//execsql will help to prepare table udsyntax
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TB_name")
    }
    fun ADD_DATA(title_text:String,desc_text:String){

        val DB=this.writableDatabase
        val values= ContentValues()
        values.put(title,title_text)
        values.put(desc,desc_text)
        DB.insert(TB_name,null,values)

    }
    val data_getter: Cursor get() {

        val DB=this.writableDatabase
        var data=DB.rawQuery("select * from " + TB_name,null)
        return data
    }

}


