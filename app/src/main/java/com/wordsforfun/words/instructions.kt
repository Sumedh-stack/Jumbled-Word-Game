package com.wordsforfun.words

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class instructions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)
        val skip=findViewById<Button>(R.id.skip)
        skip.setOnClickListener {
            startActivity(Intent(this,options::class.java))
        }
    }
}
