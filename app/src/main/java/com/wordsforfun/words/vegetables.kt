package com.wordsforfun.words



import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_actual_file.*
import kotlinx.android.synthetic.main.activity_actual_file.info
import kotlinx.android.synthetic.main.activity_countries.*
import kotlinx.android.synthetic.main.activity_vegetables.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.floor

class vegetables: AppCompatActivity() ,RewardedVideoAdListener {
    lateinit var sharetext2:String
    lateinit var rewardedvideo:ImageButton
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    var d:Int =0
    var a=1
    private var i = 0
    lateinit var level:TextView
    private var screenWidth = 0
    private var screenHeight = 0

    // Images
    private var arrowUp: ImageView? = null
    private var arrowDown: ImageView? = null
    private var arrowRight: ImageView? = null
    private var arrowLeft: ImageView? = null

    // Button
    private var pauseBtn: Button? = null

    // Position
    private var arrowUpX = 0f
    private var arrowUpY = 0f
    private var arrowDownX = 0f
    private var arrowDownY = 0f
    private var arrowRightX = 0f
    private var arrowRightY = 0f
    private var arrowLeftX = 0f
    private var arrowLeftY = 0f

    // Initialize Class
    private val handler: Handler = Handler()
    private var timer: Timer = Timer()
    // Status Check
    private var pause_flg = false
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var question_word:TextView
    private lateinit var answer_word:EditText
    private  lateinit var check_btn:Button
    private  lateinit var right_btn:Button
    private lateinit var next_btn:Button
    private lateinit var hint: ImageButton
    private  lateinit var first:String
    private var items= arrayOf(	"amarante","arrowroot","artichoke","arugula","asparagus","beans","beetroot","broccoli","cabbage","carrot","cassava","cauliflower","celeriac","celery","chayote","chicory","collards","corn","crookneck","cucumber","daikon","edamame","eggplant","fennel","fiddleheads","ginger","horseradish","jicama","kale","kohlrabi","leeks","lettuce","mushrooms","okra","onions","parsnip","peas","pepper","potato","pumpkin","radicchio","radishes","rutabaga","salsify","shallots","sorrel","spinach","squash","butternut","tomatillo","tomato","turnip","watercress","yam","zucchini")
    private  lateinit var random:Random
    private lateinit var name2:String
    private  lateinit var sumedh:TextView
    private lateinit var text2speech:TextToSpeech
    private lateinit var spell:ImageButton
    private lateinit var sound2:Button
    private lateinit var mp: MediaPlayer
    private lateinit var counter2:TextView
    private  var counter=0
    var n=0
    lateinit var mp3:MediaPlayer
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetables)
        arrowUp = findViewById(R.id.arrowUp)
        arrowDown = findViewById(R.id.arrowDown)
        arrowRight = findViewById(R.id.arrowRight)
        arrowLeft = findViewById(R.id.arrowLeft)



        // Get Screen Size.
        val wm: WindowManager = windowManager
        val disp: Display = wm.getDefaultDisplay()
        val size = Point()
        disp.getSize(size)
        screenWidth = size.x
        screenHeight = size.y

        // Move to out of screen.
        arrowUp!!.setX(-80.0f)
        arrowUp!!.setY(-80.0f)
        arrowDown!!.setX(-80.0f)
        arrowDown!!.setY(screenHeight + 80.0f)
        arrowRight!!.setX(screenWidth + 80.0f)
        arrowRight!!.setY(-80.0f)
        arrowLeft!!.setX(-80.0f)
        arrowLeft!!.setY(-80.0f)


        // Start the timer.
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post { changePos() }
            }
        }, 0, 20)

        counter2=findViewById(R.id.counter)
        sound2=findViewById(R.id.click)

        question_word=findViewById(R.id.question_word)
        answer_word=findViewById(R.id.answer_word)

        right_btn=findViewById(R.id.right_btn)
        sumedh=findViewById(R.id.sumedh2)
        next_btn=findViewById(R.id.next_btn)
        spell=findViewById(R.id.spell)
        level=findViewById(R.id.level)


        random=Random()

       // prepareAd()
        takewords()
        val start=findViewById<Button>(R.id.start)
        val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
        counter = myScore.getInt("score", 0)
        counter2.text = counter.toString()
        var myImage=findViewById<ImageButton>(R.id.bulb_animation)
        var animation=myImage.background as AnimationDrawable
        animation?.setEnterFadeDuration(1000)
        animation?.setExitFadeDuration(1000)
        animation?.start()
        val treasurescore = getSharedPreferences("trs3",MODE_PRIVATE)
        i = treasurescore.getInt("scro3", 0)
        if (counter == 0) {
            val alert = Dialog(this@vegetables)
            alert.setContentView(R.layout.instructions)
            val skip = alert.findViewById<Button>(R.id.skip)
            alert.show()
            skip.setOnClickListener {
                alert.dismiss()
            }
        }
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this

        loadRewardedVideoAd()
        rewardedvideo=findViewById(R.id.rewardedvideo)
        rewardedvideo.setOnClickListener {
            mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()



            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            }

            else {
                Toast.makeText(applicationContext,"Rewarded video is not available,Please try again later",Toast.LENGTH_SHORT).show()
            }


        }
        mp3 = MediaPlayer.create(this@vegetables, resources.getIdentifier(start3.tag as String, "raw", packageName))
        mp3.isLooping=true
        mp3.start()

        val switch=findViewById<Switch>(R.id.switchact)
        switch.isChecked=true
        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if(!switch.isChecked)
            {
                mp3.stop()
            }
            else if(switch.isChecked)
            {
                mp3 = MediaPlayer.create(this@vegetables, resources.getIdentifier(start.tag as String, "raw", packageName))
                mp3.isLooping=true
                mp3.start()
            }


        }

        treasure3.setOnClickListener {
            mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            if(i%4==0){
                i++
                val treasurescore = getSharedPreferences("trs3",MODE_PRIVATE)
                val editor6 = treasurescore.edit()
                editor6.putInt("scro3", i)
                editor6.commit()
                counter += 100
                //
                val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
                val editor = myScore.edit()
                editor.putInt("score", counter)
                editor.commit()
                this.counter2.text = counter.toString()
                //

                val alert=Dialog(this@vegetables)
                alert.setContentView(R.layout.feecoins)
                alert.show()

            }
            else{
                Toast.makeText(applicationContext,"no free coins are available",Toast.LENGTH_SHORT).show()
            }


        }
        myImage.setOnClickListener {
            mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            val alert=Dialog(this@vegetables)
            alert.setContentView(R.layout.alltexts)
            val title=alert.findViewById<TextView>(R.id.alltext)
            title.text= "The first Letter is $first"
            alert.show()



        }

        info_veg.setOnClickListener {
            mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            startActivity(Intent(this@vegetables,see_veg::class.java))
        }
        spell.setOnClickListener {
            mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            sumedh.text = answer_word.text.toString()

            if (sumedh.text == name2) {
                Toast.makeText(applicationContext, "Wait a moment", Toast.LENGTH_SHORT).show()
                text2speech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        text2speech.language = Locale.US
                        text2speech.speak(name2, TextToSpeech.QUEUE_FLUSH, null, null)
                    }

                })
            }
            else {
                val alert=Dialog(this@vegetables)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)
                title.text="Please first answer the question correctly"
                alert.show()



            }
        }
        reuse4.setOnClickListener {
            takewords()
        }
        right_btn.setOnClickListener {
            mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()

            if(counter<=0)
            {
                val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
                val editor = myScore.edit()
                editor.putInt("score", 0)
                counter=0
                editor.commit()
                this.counter2.text = 0.toString()
                Toast.makeText(applicationContext,"Coins are not sufficient to see the answer",Toast.LENGTH_SHORT).show()
            }
            else if(counter>=35)
            {
                counter -= 35
                val alert=Dialog(this@vegetables)
                alert.setContentView(R.layout.answer_word)
                val title=alert.findViewById<TextView>(R.id.correct_Ans)
                title.text=items[d]
                alert.show()
            }
            else if(counter in 1..34)
            {
                Toast.makeText(applicationContext,"Coins are not sufficient to see the answer",Toast.LENGTH_SHORT).show()
            }
            val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
            val editor = myScore.edit()
            editor.putInt("score", counter).toString()
            editor.commit()
            this.counter2.setText(counter.toString())
        }
        var level_increase=getSharedPreferences("levveg", MODE_PRIVATE)
        a=level_increase.getInt("lnoveg",1)
        level.setText(a.toString())



        next_btn.setOnClickListener {




            sumedh.text = answer_word.text.toString().toLowerCase(Locale.ROOT).trim()
            if (sumedh.text == "") {
                mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(sound2.tag as String, "raw", packageName))
                mp.start()
                val alert= Dialog(this@vegetables)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)
                title.text="The Field is empty"
                alert.show()
                text2speech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        text2speech.language = Locale.US
                        text2speech.speak("The Field is empty",TextToSpeech.QUEUE_FLUSH, null, null)
                    }

                })
                return@setOnClickListener
            }
            else if(d==items.size-1) {
                val alert = Dialog(this@vegetables)
                alert.setContentView(R.layout.allcompleted)
                val next=alert.findViewById<Button>(R.id.button2)
                next.setOnClickListener {
                    startActivity(Intent(this,options::class.java))
                }

            }
            else if (sumedh.text != name2) {
                mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(wrong3.tag as String, "raw", packageName))
                mp.start()
                val alert= Dialog(this@vegetables)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)
                title.text="Wrong Answer"
                alert.show()
                return@setOnClickListener
            }
            else {
                mp = MediaPlayer.create(this@vegetables, resources.getIdentifier(right3.tag as String, "raw", packageName))
                mp.start()
                val DB=SQHELperveg(applicationContext)
                val word_input=items[d].trim()
                val meaning_input=" "
                DB.ADD_DATA(word_input,meaning_input)
                d++
                if(d%5==0&&d>0)
                {

                    a++
                    n=0

                    if(a%1==0)
                    {
                        if(i%4!=0&&i>0)
                        {
                            i += 3
                            val treasurescore = getSharedPreferences("trs3",MODE_PRIVATE)
                            val editor6 = treasurescore.edit()
                            editor6.putInt("scro3", i)
                            editor6.commit()
                        }
                    }
                    val alert= Dialog(this@vegetables)
                    alert.setContentView(R.layout.levelcompleted)
                    alert.show()
                    val nxtlevel = alert.findViewById<Button>(R.id.next_level)
                        alert.show()
                        nxtlevel.setOnClickListener {
//                            if (a >5 && n == 0) {
//
//                                if (mInterstitialAd.isLoaded) {
//                                    mInterstitialAd.show()
//                                    n++
//                                }
//
//                            }
                        alert.dismiss()
                    }
                    var level_increase=getSharedPreferences("levveg", MODE_PRIVATE)
                    var editor3=level_increase.edit()
                    editor3.putInt("lnoveg",a)
                    editor3.commit()
                    level.text = a.toString()
                    if (sumedh.text == name2) {
                        text2speech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
                            if (status == TextToSpeech.SUCCESS) {
                                text2speech.language = Locale.US
                                text2speech.speak("Congratulations Level Completed",TextToSpeech.QUEUE_FLUSH, null, null)
                            }

                        })
                    }
                }

                var myqno=getSharedPreferences("helloveg", MODE_PRIVATE)
                var editor2=myqno.edit()
                editor2.putInt("noveg",d)
                editor2.commit()
                counter += 15
                val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
                val editor = myScore.edit()
                editor.putInt("score", counter).toString()
                editor.commit()
                this.counter2.text = counter.toString()
                takewords()

                answer_word.text = null

            }

        }





    }
    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-2474376873043059/4493838346",AdRequest.Builder().build())
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater2=menuInflater
        inflater2.inflate(R.menu.customfile,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {

            R.id.Countries->{
                val intent2=Intent(this@vegetables, Countries::class.java)
                startActivity(intent2)
                return true
            }
            R.id.Words->{
                val intent7=Intent(this@vegetables, actual_file::class.java)
                startActivity(intent7)
                return true
            }
            R.id.WildLife->{
                val intent3=Intent(this@vegetables, WildLife::class.java)
                startActivity(intent3)
                return true
            }

            R.id.Vegetables->{
                val intent5=Intent(this@vegetables, vegetables::class.java)
                startActivity(intent5)
                return true
            }
            R.id.Fruits->{
                val intent5=Intent(this@vegetables, fruits::class.java)
                startActivity(intent5)
                return true

            }


        }


        return true

    }
    private fun takewords() {
        var myqno=getSharedPreferences("helloveg", MODE_PRIVATE)
        d=myqno.getInt("noveg",0)
        var t= items[d].toString().trim()
        name2=items[d]
        first=items[d][0].toString()
        var editor=myqno.edit()
        editor.putInt("no",d)
        editor.commit()
        question_word.text = MixWords(t)
        sharetext2=question_word.text.toString()
    }
    fun MixWords(word:String):String {

        var words=Arrays.asList<String>(*word.split("".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        words.shuffle()
        var mix=""
        for( int in words)
            mix = mix + int
        return mix
    }
//    fun prepareAd() {
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId ="ca-app-pub-2474376873043059/1300698798"
//        mInterstitialAd.loadAd(AdRequest.Builder().build())
//    }
    fun changePos() {
        // Up
        arrowUpY -= 10
        if (arrowUp!!.y + arrowUp!!.height < 0) {
            arrowUpX =
                floor(Math.random() * (screenWidth - arrowUp!!.width)).toFloat()
            arrowUpY = screenHeight + 100.0f
        }
        arrowUp!!.x = arrowUpX
        arrowUp!!.y = arrowUpY

        // Down
        arrowDownY += 10
        if (arrowDown!!.y > screenHeight) {
            arrowDownX =
                Math.floor(Math.random() * (screenWidth - arrowDown!!.width)).toFloat()
            arrowDownY = -100.0f
        }
        arrowDown!!.x = arrowDownX
        arrowDown!!.y = arrowDownY

        // Right
        arrowRightX += 10
        if (arrowRight!!.x > screenWidth) {
            arrowRightX = -100.0f
            arrowRightY =
                Math.floor(Math.random() * (screenHeight - arrowRight!!.height)).toFloat()
        }
        arrowRight!!.x = arrowRightX
        arrowRight!!.y = arrowRightY

        // Left
        arrowLeftX -= 10
        if (arrowLeft!!.x + arrowLeft!!.width < 0) {
            arrowLeftX = screenWidth + 100.0f
            arrowLeftY =
                Math.floor(Math.random() * (screenHeight - arrowLeft!!.height)).toFloat()
        }
        arrowLeft!!.x = arrowLeftX
        arrowLeft!!.y = arrowLeftY
    }
    override fun onRewardedVideoAdClosed() {

        loadRewardedVideoAd()

    }

    override fun onRewardedVideoAdLeftApplication() {
        Toast.makeText(applicationContext, "Ad left application.", Toast.LENGTH_SHORT).show()
    }
    override fun onRewardedVideoAdLoaded() {
        TODO("Not yet implemented")
    }
    override fun onRewardedVideoAdOpened() {
        Toast.makeText(baseContext, "Ad opened.", Toast.LENGTH_SHORT).show()
    }
    override fun onRewardedVideoCompleted() {
        Toast.makeText(baseContext, "Ad completed.", Toast.LENGTH_SHORT).show()
    }
    override fun onRewarded(p0: RewardItem?) {
        val alert=Dialog(this@vegetables)
        alert.setContentView(R.layout.coinsincrease)
        val claim=alert.findViewById<Button>(R.id.claim)
        alert.show()
        claim.setOnClickListener {
            startActivity(Intent(this,vegetables::class.java))
            counter += 100
            val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
            val editor = myScore.edit()
            editor.putInt("score", counter).toString()
            editor.commit()
            this.counter2.setText(counter.toString())
        }
    }
    override fun onRewardedVideoStarted() {
        Toast.makeText(applicationContext, "Ad opened.", Toast.LENGTH_SHORT)
    }
    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        Toast.makeText(baseContext, "Ad failed to load.", Toast.LENGTH_SHORT)
    }

        fun onClickWhatsApp(view: View?) {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "What is the correct answer for the jumbled word '$sharetext2' ? hint(it's a name of a vegetable)"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share Via :"))

        }
    override fun onBackPressed() {
        startActivity(Intent(this@vegetables,options::class.java))
        return
    }
    override fun onPause() {
        super.onPause()
        mRewardedVideoAd.pause(this)
        mp3.stop()
    }
    override fun onResume() {
        super.onResume()
        mRewardedVideoAd.resume(this)
        if (mp3 != null && !mp3.isPlaying)
        {
            mp3 = MediaPlayer.create(
                this@vegetables,
                resources.getIdentifier(start3.tag as String, "raw", packageName)
            )
            mp3.isLooping = true
            mp3.start()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd.destroy(this)
    }
}














