package com.wordsforfun.words


import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.settingsbar.*


class MainActivity : AppCompatActivity() {
    lateinit var mp3:MediaPlayer
    lateinit var mp2:MediaPlayer
    lateinit var alert:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<Button>(R.id.startg)
        var btn2=findViewById<Button>(R.id.btn2)
        mp3= MediaPlayer.create(this@MainActivity, resources.getIdentifier(btn.tag as String, "raw", packageName))
        mp3.isLooping=true
        mp3.start()


        buttong.setOnClickListener {
            alert = Dialog(this@MainActivity)
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
        inst.setOnClickListener {
            var intent = Intent(this@MainActivity,instructions::class.java)
            startActivity(intent)
        }
        btn.setOnClickListener {
            mp2= MediaPlayer.create(this@MainActivity,resources.getIdentifier(btn2.tag as String,"raw",packageName))
            mp2.start()

            var intent = Intent(this@MainActivity,options::class.java)
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater2=menuInflater
        inflater2.inflate(R.menu.customfile2,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.instructions -> {
                val intent10= Intent(this@MainActivity,instructions::class.java)
                startActivity(intent10)
                return true
            }
            R.id.moreinfo -> {
                val intent11= Intent(this@MainActivity,more_info::class.java)
                startActivity(intent11)
                return true
            }

        }
        return true
    }
    fun onClickWhatsApp(view: View?) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody =   "This is the GAME OF WORDS WITH MEANINGS AND LOT MORE INSIDE IT !\n https://play.google.com/store/apps/details?id=com.wordsforfun.words"

        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share Via :"))

    }
    override fun onPause() {
        super.onPause()
        mp3.stop()
    }
    override fun onResume() {
        super.onResume()
        if (mp3 != null && !mp3.isPlaying)
            {
                mp3= MediaPlayer.create(this@MainActivity, resources.getIdentifier(startg.tag as String, "raw", packageName))
                mp3.isLooping=true
                mp3.start()
            }
    }
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        }

}
