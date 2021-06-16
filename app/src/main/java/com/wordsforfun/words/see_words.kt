package com.wordsforfun.words

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_see_words.*

class see_words : AppCompatActivity() {
    lateinit var lists: ArrayList<Subject>
    lateinit var DB:SQHelper
    lateinit var data: Cursor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_words)

        val go =findViewById<Button>(R.id.go)

        go.setOnClickListener{
            var intent = Intent(this@see_words,actual_file::class.java)
            startActivity(intent)
        }
        lists=ArrayList<Subject>()
        DB= SQHelper(applicationContext)
        data=DB.data_getter
        val adapter2=Adapter(applicationContext,lists)
        val recyclerView=findViewById<RecyclerView>(R.id.list)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView.adapter=adapter2
        showData()

    }
    fun showData()
    {
        if(data.count==0)
        {
            Toast.makeText(applicationContext,"All words which are completed will be seen here", Toast.LENGTH_SHORT).show()

        }

        while(data.moveToNext()){
            lists.add(Subject(data.getString(0),data.getString(1),data.getString(2)))


        }





    }
    override fun onBackPressed() {
        startActivity(Intent(this@see_words,actual_file::class.java))
        return
    }
    override fun onStart() {
        super.onStart()
        showData()
    }



}


