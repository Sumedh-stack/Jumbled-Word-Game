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
import kotlinx.android.synthetic.main.activity_wildlife.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class WildLife : AppCompatActivity(),RewardedVideoAdListener {

    lateinit var rewardedvideo:ImageButton
    var d:Int =0
    var a=1
    private lateinit var mRewardedVideoAd: RewardedVideoAd
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
   // private lateinit var mInterstitialAd: InterstitialAd
    lateinit var question_word:TextView
    lateinit var answer_word:EditText
    lateinit var check_btn:Button
    lateinit var right_btn:Button
    lateinit var next_btn:Button
    lateinit var first:String
    lateinit var hint: ImageButton
    var items= arrayOf("dog","puppy","turtle","parrot","cat","kitten","goldfish","mouse","hamster","cow","rabbit","ducks","shrimp","pig","goat","crab","deer","bee","sheep","fish","turkey","dove","chicken","horse","squirrel","dog","chimpanzee","ox","lion","panda","walrus","otter","mouse","kangaroo","goat","horse","monkey","cow","koala","mole","elephant","leopard","hippopotamus","giraffe","fox","coyote","hedgehong","sheep","deer","crow","peacock","dove","sparrow","goose","stork","pigeon","turkey","hawk","eagle","raven","parrot","flamingo","seagull","ostrich","swallow","penguin","robin","swan","owl","woodpecker","giraffe","woodpecker","camel","starfish","alligator","owl","tiger","bear","coyote","chimpanzee","raccoon","lion","wolf","crocodile","dolphin","elephant","squirrel","snake","kangaroo","elk","fox","gorilla","bat","hare","toad","frog","deer","rat","badger","lizard","mole","hedgehog","otter","reindeer","crab","fish","seal","octopus","shark","seahorse","walrus","starfish","whale","penguin","jellyfish","squid","lobster","pelican","clams","seagull","dolphin","shells","urchin","cormorant","otter","pelican","anemone","coral","moth","bee","butterfly","spider","ladybird","ant","dragonfly","fly","mosquito","grasshopper","beetle","cockroach","centipede","worm","louse")
    lateinit var random:Random
    lateinit var name2:String
    lateinit var sumedh:TextView
    lateinit var text2speech:TextToSpeech
    lateinit var spell:ImageButton
    var n=0
    lateinit var sound2:Button
    lateinit var mp: MediaPlayer
    lateinit var counter2:TextView
    var counter=0
    var i=0
    lateinit var mp3:MediaPlayer
    lateinit var sharetext2:String
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wildlife)
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

        question_word=findViewById(R.id.question_word)
        answer_word=findViewById(R.id.answer_word)
        sound2=findViewById(R.id.click)

        right_btn=findViewById(R.id.right_btn)
        sumedh=findViewById(R.id.sumedh2)
        next_btn=findViewById(R.id.next_btn)
        counter2=findViewById(R.id.counter)
        random=Random()

        level=findViewById(R.id.level)
        val start=findViewById<Button>(R.id.start)
        //prepareAd()

        takewords()

        val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
        counter = myScore.getInt("score", 0)
        counter2.text = counter.toString()
        spell=findViewById(R.id.spell)

        var myImage=findViewById<ImageButton>(R.id.bulb_animation)
        var animation=myImage.background as AnimationDrawable
        animation?.setEnterFadeDuration(1000)
        animation?.setExitFadeDuration(1000)
        animation?.start()
        val treasurescore = getSharedPreferences("trs5",MODE_PRIVATE)
        i = treasurescore.getInt("scro5", 0)
        if (counter == 0) {
            val alert = Dialog(this@WildLife)
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


            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            }

            else {
                Toast.makeText(applicationContext,"Rewarded video is not available,Please try again later",Toast.LENGTH_SHORT).show()
            }


        }

        mp3 = MediaPlayer.create(this@WildLife, resources.getIdentifier(start5.tag as String, "raw", packageName))
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
                mp3 = MediaPlayer.create(this@WildLife, resources.getIdentifier(start.tag as String, "raw", packageName))
                mp3.isLooping=true
                mp3.start()
            }


        }
        reuse5.setOnClickListener {
            takewords()
        }
        treasure4.setOnClickListener {
            mp = MediaPlayer.create(this@WildLife, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            if(i%4==0){
                i++
                val treasurescore = getSharedPreferences("trs5",MODE_PRIVATE)
                val editor6 = treasurescore.edit()
                editor6.putInt("scro5", i)
                editor6.commit()
                counter += 100
                val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
                val editor = myScore.edit()
                editor.putInt("score", counter).toString()
                editor.commit()
                this.counter2.setText(counter.toString())
                val alert=Dialog(this@WildLife)
                alert.setContentView(R.layout.feecoins)
                alert.show()

            }
            else{
                Toast.makeText(applicationContext,"no free coins are available",Toast.LENGTH_SHORT).show()
            }


        }
        myImage.setOnClickListener {
            mp = MediaPlayer.create(this@WildLife, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            val alert=Dialog(this@WildLife)
            alert.setContentView(R.layout.alltexts)
            val title=alert.findViewById<TextView>(R.id.alltext)
            title.text= "The first Letter is $first"
            alert.show()



        }
        info_wild.setOnClickListener {
            mp = MediaPlayer.create(this@WildLife, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            startActivity(Intent(this@WildLife,see_wildlife::class.java))
        }
        spell.setOnClickListener {
            mp = MediaPlayer.create(this@WildLife, resources.getIdentifier(sound2.tag as String, "raw", packageName))
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
                val alert=Dialog(this@WildLife)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)
                title.text="Please first answer the question correctly"
                alert.show()



            }
        }

        right_btn.setOnClickListener {
            mp = MediaPlayer.create(this@WildLife, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()

            if(counter<=0)
            {
                val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
                val editor = myScore.edit()
                editor.putInt("score", counter).toString()
                counter=0
                editor.commit()
                this.counter2.setText(counter.toString())
                Toast.makeText(applicationContext,"Coins are not sufficient to see the answer",Toast.LENGTH_SHORT).show()
            }
            else if(counter in 1..34)
            {
                Toast.makeText(applicationContext,"Coins are not sufficient to see the answer",Toast.LENGTH_SHORT).show()
            }
            else if(counter>=35)
            {
                counter -= 35
                val alert=Dialog(this@WildLife)
                alert.setContentView(R.layout.answer_word)
                val title=alert.findViewById<TextView>(R.id.correct_Ans)
                title.text=items[d]
                alert.show()
            }

            val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
            val editor = myScore.edit()
            editor.putInt("score", counter).toString()
            editor.commit()
            this.counter2.setText(counter.toString())
        }
        var level_increase=getSharedPreferences("levwild", MODE_PRIVATE)
        a=level_increase.getInt("lnowild",1)
        level.setText(a.toString())





        next_btn.setOnClickListener {



            sumedh.text = answer_word.text.toString().toLowerCase(Locale.ROOT).trim()
            if (sumedh.text == "") {
                mp = MediaPlayer.create(this@WildLife, resources.getIdentifier(sound2.tag as String, "raw", packageName))
                mp.start()
                val alert= Dialog(this@WildLife)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)
                title.text="The Field is empty"
                alert.show()
                text2speech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        text2speech.language = Locale.US
                        text2speech.speak("The Field is empty",TextToSpeech.QUEUE_FLUSH, null, null)
                       // The Field is empty
                    }

                })
                return@setOnClickListener
            } else if (sumedh.text != name2) {
                mp = MediaPlayer.create(this@WildLife, resources.getIdentifier(wrong4.tag as String, "raw", packageName))
                mp.start()
                val alert= Dialog(this@WildLife)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)

                title.text="Wrong Answer"
                alert.show()
                return@setOnClickListener
            }
            else if(d==items.size-1) {
                val alert = Dialog(this@WildLife)
                alert.setContentView(R.layout.allcompleted)
                alert.show()
                val next=alert.findViewById<Button>(R.id.button2)
                next.setOnClickListener {
                    startActivity(Intent(this,options::class.java))

                }

            }
            else {
                mp = MediaPlayer.create(this@WildLife, resources.getIdentifier(right4.tag as String, "raw", packageName))
                mp.start()
                val DB=Sqhelperwildlife(applicationContext)
                val word_input=items[d].trim()
                val meaning_input=""
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
                            val treasurescore = getSharedPreferences("trs5",MODE_PRIVATE)
                            val editor6 = treasurescore.edit()
                            editor6.putInt("scro5", i)
                            editor6.commit()
                        }
                    }
                    val alert= Dialog(this@WildLife)
                    alert.setContentView(R.layout.levelcompleted)
                    val nxtlevel=alert.findViewById<Button>(R.id.next_level)
                    alert.show()
                    nxtlevel.setOnClickListener {
//                        if(a>8&&n==0)
//                        {
//
//                            if (mInterstitialAd.isLoaded) {
//                                mInterstitialAd.show()
//                                n++
//                            }
//
//                        }
                        alert.dismiss()
                    }
                    var level_increase=getSharedPreferences("levwild", MODE_PRIVATE)
                    var editor3=level_increase.edit()
                    editor3.putInt("lnowild",a)
                    editor3.commit()
                    level.text = a.toString()
                    if (sumedh.text == name2) {
                        text2speech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
                            if (status == TextToSpeech.SUCCESS) {
                                text2speech.language = Locale.UK
                                text2speech.speak("Congratulations Level Completed",TextToSpeech.QUEUE_FLUSH, null, null)

                            }

                        })
                    }
                }

                var myqno=getSharedPreferences("hellowild", MODE_PRIVATE)
                var editor2=myqno.edit()
                editor2.putInt("nowild",d)
                editor2.commit()
                counter += 15
                val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
                val editor = myScore.edit()
                editor.putInt("score", counter).toString()
                editor.commit()
                this.counter2.setText(counter.toString())

                takewords()

                answer_word.text = null

            }

        }




    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater2=menuInflater
        inflater2.inflate(R.menu.customfile,menu)
        return true
    }
    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-2474376873043059/4493838346",AdRequest.Builder().build())
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {

            R.id.Words->{
                val intent7=Intent(this@WildLife, actual_file::class.java)
                startActivity(intent7)
                return true
            }
            R.id.Countries->{
                val intent2=Intent(this@WildLife, Countries::class.java)
                startActivity(intent2)
                return true
            }
            R.id.WildLife->{
                val intent3=Intent(this@WildLife, WildLife::class.java)
                startActivity(intent3)
                return true
            }

            R.id.Vegetables->{
                val intent5=Intent(this@WildLife, vegetables::class.java)
                startActivity(intent5)
                return true
            }
            R.id.Fruits->{
                val intent5=Intent(this@WildLife, fruits::class.java)
                startActivity(intent5)
                return true

            }

        }


        return true

    }
    private fun takewords() {
        var myqno=getSharedPreferences("hellowild", MODE_PRIVATE)
        d=myqno.getInt("nowild",0)
        var t= items[d].toString().trim()
        name2=items[d]
        first=items[d][0].toString()
        var editor=myqno.edit()
        editor.putInt("nowild",d)
        editor.commit()
        question_word.text = MixWords(t)
        sharetext2=question_word.text.toString()

    }
    fun MixWords(word:String):String {

        var words=Arrays.asList<String>(*word.split("".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        words.shuffle()
        var mix=""
        for( int in words)
            mix += int
        return mix
    }
//   fun prepareAd() {
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = "ca-app-pub-2474376873043059/1300698798"
//        mInterstitialAd.loadAd(AdRequest.Builder().build())
//    }
    fun changePos() {

        arrowUpY -= 10
        if (arrowUp!!.y + arrowUp!!.height < 0) {
            arrowUpX =
                Math.floor(Math.random() * (screenWidth - arrowUp!!.width)).toFloat()
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
        val alert=Dialog(this@WildLife)
        alert.setContentView(R.layout.coinsincrease)
        val claim=alert.findViewById<Button>(R.id.claim)
        alert.show()
        claim.setOnClickListener {
            startActivity(Intent(this,WildLife::class.java))
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
        val shareBody = "What is the correct answer for the jumbled word '$sharetext2' ? hint(it is related to wildlife)"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share Via :"))





    }
    override fun onBackPressed() {
        startActivity(Intent(this@WildLife,options::class.java))
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
        if (!mp3.isPlaying)
        {
            mp3 = MediaPlayer.create(
                this@WildLife,
                resources.getIdentifier(start5.tag as String, "raw", packageName)
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











