package com.wordsforfun.words

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.wordsforfun.words.Countries
import com.wordsforfun.words.WildLife
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_options.*
import kotlinx.android.synthetic.main.settingsbar.*

class options : AppCompatActivity() {
    lateinit var words: Button
    lateinit var countries: Button
    lateinit var fruits: Button
    lateinit var vegetables: Button
    lateinit var wildlife: Button
    lateinit var mp7:MediaPlayer
    lateinit var mp8:MediaPlayer
    lateinit var mp9:MediaPlayer
    lateinit var mp10:MediaPlayer
    lateinit var mp11:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        words=findViewById(R.id.Words)
        countries=findViewById(R.id.Countries)
        fruits=findViewById(R.id.Fruits)
        vegetables=findViewById(R.id.Vegetables)
        wildlife=findViewById(R.id.WildLife)
        buttong2.setOnClickListener {
            val alert = Dialog(this@options)
            alert.setContentView(R.layout.settingsbar)
            alert.show()
            alert.button.setOnClickListener {
                var intent = Intent(Intent.ACTION_SEND)
                var recipients = arrayOf("appson321@gmail.com")
                intent.putExtra(Intent.EXTRA_EMAIL, recipients)
                intent.putExtra(Intent.EXTRA_SUBJECT, "")
                intent.putExtra(Intent.EXTRA_TEXT, "")
                intent.putExtra(Intent.EXTRA_CC, "appson321@gmail.com")
                intent.setType("text/html")
                intent.setPackage("com.google.android.gm")
                startActivity(intent)

            }
        }
        words.setOnClickListener {
            mp7= MediaPlayer.create(this@options, resources.getIdentifier(words.tag as String, "raw", packageName))
            mp7.start()
            startActivity(Intent(this@options,actual_file::class.java))

        }
        countries.setOnClickListener {
            mp8= MediaPlayer.create(this@options, resources.getIdentifier(countries.tag as String, "raw", packageName))
            mp8.start()
            startActivity(Intent(this@options,com.wordsforfun.words.Countries::class.java))
        }
        fruits.setOnClickListener {
            mp9= MediaPlayer.create(this@options, resources.getIdentifier(fruits.tag as String, "raw", packageName))
            mp9.start()
            startActivity(Intent(this@options,com.wordsforfun.words.fruits::class.java))
        }
        vegetables.setOnClickListener {
            mp10= MediaPlayer.create(this@options, resources.getIdentifier(vegetables.tag as String, "raw", packageName))
            mp10.start()
            startActivity(Intent(this@options,com.wordsforfun.words.vegetables::class.java))
        }
        wildlife.setOnClickListener {
            mp11= MediaPlayer.create(this@options, resources.getIdentifier(wildlife.tag as String, "raw", packageName))
            mp11.start()
            startActivity(Intent(this@options,com.wordsforfun.words.WildLife::class.java))
        }

    }
    fun onClickWhatsApp(view: View?) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody =   "This is the GAME OF WORDS WITH MEANINGS AND LOT MORE INSIDE IT !\n https://play.google.com/store/apps/details?id=com.wordsforfun.words"

        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share Via :"))

    }
    override fun onBackPressed() {
        startActivity(Intent(this@options,MainActivity::class.java))
        return
    }

}