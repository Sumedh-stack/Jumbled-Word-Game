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
import kotlinx.android.synthetic.main.activity_see_fruits.*
import kotlinx.android.synthetic.main.activity_see_veg.*
import kotlinx.android.synthetic.main.activity_see_wildlife.*

class see_veg : AppCompatActivity() {

        lateinit var lists: ArrayList<Subject2>
        lateinit var DB:SQHELperveg
        lateinit var data: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_veg)
            val go =findViewById<Button>(R.id.go5)

            go.setOnClickListener{
                var intent = Intent(this@see_veg,vegetables::class.java)
                startActivity(intent)
            }
            lists=ArrayList<Subject2>()
            DB= SQHELperveg(applicationContext)
            data=DB.data_getter

        val adapter2=Adapter2(applicationContext,lists)
        val recyclerView=findViewById<RecyclerView>(R.id.list5)
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
                lists.add(Subject2(data.getString(0),data.getString(1),data.getString(2)))


            }





        }

        override fun onStart() {
            super.onStart()
            showData()
        }
    override fun onBackPressed() {
        startActivity(Intent(this@see_veg,vegetables::class.java))
        return
    }


    }


