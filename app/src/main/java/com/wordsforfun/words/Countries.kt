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

class Countries : AppCompatActivity() , RewardedVideoAdListener {
  private  var d:Int =0
   private var a=1
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    private lateinit var level:TextView
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
   private lateinit var question_word:TextView
    private lateinit var answer_word:EditText
    private lateinit var check_btn:Button
    private lateinit var right_btn:Button
    private  lateinit var next_btn:Button
    private  var items= arrayOf ("afghanistan",
        "tajikistan",
        "brunei",
        "estonia",
        "yemen",
        "armenia",
        "nepal",
        "poland",
        "mexico",
        "seychelles",
        "lesotho",
        "mali",
        "kenya",
        "argentina",
        "ecuador",
        "nigeria",
        "grenada",
        "samoa",
        "togo",
        "ethiopia",
        "zimbabwe",
        "philippines",
        "peru",
        "romania",
        "uruguay",
        "mongolia",
        "taiwan",
        "albania",
        "bangladesh",
        "jordan",
        "chile",
        "guatemala",
        "malaysia",
        "senegal",
        "malawi",
        "montenegro",
        "ghana",
        "azerbaijan",
        "monaco",
        "czechia",
        "eritrea",
        "cambodia",
        "turkey",
        "gabon",
        "algeria",
        "tunisia",
        "madagascar",
        "iran",
        "pakistan",
        "sweden",
        "syria",
        "latvia",
        "kiribati",
        "kuwait",
        "micronesia",
        "colombia",
        "hungary",
        "israel",
        "brazil",
        "vietnam",
        "norway",
        "tanzania",
        "iraq",
        "jamaica",
        "nicaragua",
        "fiji",
        "cuba",
        "germany",
        "guinea",
        "palestine",
        "botswana",
        "canada",
        "singapore",
        "moldova",
        "australia",
        "barbados",
        "portugal",
        "turkmenistan",
        "niger",
        "russia",
        "qatar",
        "france",
        "maldives",
        "japan",
        "angola",
        "paraguay",
        "austria",
        "indonesia",
        "uzbekistan",
        "djibouti",
        "greece",
        "lebanon",
        "namibia",
        "serbia",
        "suriname",
        "rwanda",
        "uganda",
        "finland",
        "somalia",
        "burundi",
        "belize",
        "denmark",
        "ukraine",
        "haiti",
        "luxembourg",
        "eswatini",
        "kyrgyzstan",
        "libya",
        "benin",
        "croatia",
        "liechtenstein",
        "venezuela",
        "cameroon",
        "iceland",
        "georgia",
        "slovenia",
        "switzerland",
        "italy",
        "india",
        "kosovo",
        "bahamas",
        "zambia",
        "egypt",
        "andorra",
        "slovakia",
        "mozambique",
        "gambia",
        "thailand",
        "nauru",
        "belgium",
        "bahrain",
        "sudan",
        "ireland",
        "morocco",
        "spain",
        "mauritania",
        "panama",
        "china",
        "tonga",
        "myanmar",
        "dominica",
        "liberia",
        "belarus",
        "bulgaria",
        "laos",
        "malta",
        "palau",
        "guyana",
        "cyprus",
        "chad",
        "honduras",
        "vanuatu",
        "kazakhstan",
        "tuvalu",
        "oman",
        "comoros",
        "mauritius",
        "bhutan",
        "lithuania",
        "netherlands")
    private  var items2= arrayOf("Kabul",
        "Dushanbe",
        "Bandar Seri Begawan",
        "Tallinn",
        "Sana'a",
        "Yerevan",
        "Kathmandu",
        "Warsaw",
        "Mexico City",
        "Victoria",
        "Maseru",
        "Bamako",
        "Nairobi",
        "Buenos Aires",
        "Quito",
        "Abuja",
        "Saint George's",
        "Apia",
        "Lomé",
        "Addis Ababa",
        "Harare",
        "Manila",
        "Lima",
        "Bucharest",
        "Montevideo",
        "Ulaanbaatar",
        "Taipei",
        "Tirana",
        "Dhaka",
        "Amman",
        "Santiago",
        "Guatemala City",
        "Kuala Lumpur",
        "Dakar",
        "Lilongwe",
        "Podgorica",
        "Accra",
        "Baku",
        "Monaco",
        "Prague",
        "Asmara",
        "Phnom Penh",
        "Ankara",
        "Libreville",
        "Algiers",
        "Tunis",
        "Antananarivo",
        "Tehran",
        "Islamabad",
        "Stockholm",
        "Damascus",
        "Riga",
        "Tarawa",
        "Kuwait City",
        "Palikir",
        "Bogotá",
        "Budapest",
        "Jerusalem",
        "Brasilia",
        "Hanoi",
        "Oslo",
        "Dodoma",
        "Baghdad",
        "Kingston",
        "Managua",
        "Suva",
        "Havana",
        "Berlin",
        "Conakry",
        "Jerusalem (East)",
        "Gaborone",
        "Ottawa",
        "Singapore",
        "Chisinau",
        "Canberra",
        "Bridgetown",
        "Lisbon",
        "Ashgabat",
        "Niamey",
        "Moscow",
        "Doha",
        "Paris",
        "Male",
        "Tokyo",
        "Luanda",
        "Asunción",
        "Vienna",
        "Jakarta",
        "Tashkent",
        "Djibouti (city)",
        "Athens",
        "Beirut",
        "Windhoek",
        "Belgrade",
        "Paramaribo",
        "Kigali",
        "Kampala",
        "Helsinki",
        "Mogadishu",
        "Gitega",
        "Belmopan",
        "Copenhagen",
        "Kyiv (also known as Kiev)",
        "Port-au-Prince",
        "Luxembourg (city)",
        "Mbabane (administrative),",
        "Bishkek",
        "Tripoli",
        "Porto-Novo",
        "Zagreb",
        "Vaduz",
        "Caracas",
        "Yaounde",
        "Reykjavik",
        "Tbilisi",
        "Ljubljana",
        "Bern",
        "Rome",
        "New Delhi",
        "Pristina",
        "Nassau",
        "Lusaka",
        "Cairo",
        "Andorra la Vella",
        "Bratislava",
        "Maputo",
        "Banjul",
        "Bangkok",
        "Yaren District (de facto)",
        "Brussels",
        "Manama",
        "Khartoum",
        "Dublin",
        "Rabat",
        "Madrid",
        "Nouakchott",
        "Panama City",
        "Beijing",
        "Nukuʻalofa",
        "Naypyidaw",
        "Roseau",
        "Monrovia",
        "Minsk",
        "Sofia",
        "Vientiane",
        "Valletta",
        "Ngerulmud",
        "Georgetown",
        "Nicosia",
        "N'Djamena",
        "Tegucigalpa",
        "Port Vila",
        "Nur-Sultan",
        "Funafuti",
        "Muscat",
        "Moroni",
        "Port Louis",
        "Thimphu",
        "Vilnius",
        "Amsterdam")
    private lateinit var random:Random
    private lateinit var name2:String
    private lateinit var sumedh:TextView
    private lateinit var text2speech:TextToSpeech
    private lateinit var spell:ImageButton
    private lateinit var counter2:TextView
    private lateinit var sound2:Button
    lateinit var rewardedvideo:ImageButton
    private lateinit var mp: MediaPlayer
    private var counter=0
    private  var n=0
   private var i = 0
    lateinit var sharetext2:String
    private lateinit var first:String
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    lateinit var mp3:MediaPlayer
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
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

        right_btn=findViewById(R.id.right_btn)
        sound2=findViewById(R.id.click)
        sumedh=findViewById(R.id.sumedh2)
        next_btn=findViewById(R.id.next_btn)
        counter2=findViewById(R.id.counter)
        var myImage=findViewById<ImageButton>(R.id.bulb_animation)
        var animation=myImage.background as AnimationDrawable
        animation?.setEnterFadeDuration(1000)
        animation?.setExitFadeDuration(1000)
        animation?.start()
        random=Random()
        level=findViewById(R.id.level)
        //prepareAd()
        takewords()
        val start=findViewById<Button>(R.id.start)
        val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
        counter = myScore.getInt("score", 0)
        counter2.text = counter.toString()

        spell=findViewById(R.id.spell)
        var treasurescore = getSharedPreferences("trs1",MODE_PRIVATE)
        i = treasurescore.getInt("scro1", 0)
        if (counter == 0) {
            val alert = Dialog(this@Countries)
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
            mp = MediaPlayer.create(this@Countries, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()

            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            }

            else {
                Toast.makeText(applicationContext,"Rewarded video is not available,please try again later",Toast.LENGTH_LONG).show()
            }


        }
        mp3 = MediaPlayer.create(this@Countries, resources.getIdentifier(start4.tag as String, "raw", packageName))
        mp3.isLooping=true
        mp3.start()

        var level_increase = getSharedPreferences("levcount", MODE_PRIVATE)
        a = level_increase.getInt("lnocount", 1)
        level.text = a.toString()
        val switch=findViewById<Switch>(R.id.switchact)
        switch.isChecked=true
        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if(!switch.isChecked)
            {
                mp3.stop()
            }
            else if(switch.isChecked)
            {
                mp3 = MediaPlayer.create(this@Countries, resources.getIdentifier(start.tag as String, "raw", packageName))
                mp3.isLooping=true
                mp3.start()
            }


        }

        treasure5.setOnClickListener {
            mp = MediaPlayer.create(this@Countries, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            if(i%4==0){
                i++
                val treasurescore = getSharedPreferences("trs1",MODE_PRIVATE)
                val editor6 = treasurescore.edit()
                editor6.putInt("scro1", i)
                editor6.commit()
                counter += 100
                val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
                val editor = myScore.edit()
                editor.putInt("score", counter).toString()
                editor.commit()
                this.counter2.setText(counter.toString())
                val alert=Dialog(this@Countries)
                alert.setContentView(R.layout.feecoins)
                alert.show()


            }
            else{
                Toast.makeText(applicationContext,"no free coins are available",Toast.LENGTH_SHORT).show()
            }


        }
        myImage.setOnClickListener {
            mp = MediaPlayer.create(this@Countries, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()
            val alert=Dialog(this@Countries)
            alert.setContentView(R.layout.alltexts)
            val title=alert.findViewById<TextView>(R.id.alltext)
            title.text= "The first Letter is $first"
            alert.show()



        }
        info_countries.setOnClickListener {
            startActivity(Intent(this@Countries,see_capitals::class.java))
        }
        spell.setOnClickListener {
            mp = MediaPlayer.create(this@Countries, resources.getIdentifier(sound2.tag as String, "raw", packageName))
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
                val alert=Dialog(this@Countries)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)
                title.text="Please first answer the question correctly"
                alert.show()



            }
        }
        reuse2.setOnClickListener {
            takewords()
        }

        right_btn.setOnClickListener {
            mp = MediaPlayer.create(this@Countries, resources.getIdentifier(sound2.tag as String, "raw", packageName))
            mp.start()

            if(counter<0)
            {
                val myScore = getSharedPreferences("gamescore", MODE_PRIVATE)
                val editor = myScore.edit()
                editor.putInt("score", 0).toString()
                counter = 0
                editor.commit()
                this.counter2.text = 0.toString()
                Toast.makeText(applicationContext,"Coins are not sufficient to see the answer",Toast.LENGTH_SHORT).show()
            }
            else if(counter>=35)
            {
                counter -= 35
                val alert=Dialog(this@Countries)
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
        next_btn.setOnClickListener {



            sumedh.text = answer_word.text.toString().toLowerCase(Locale.ROOT).trim()
            if (sumedh.text == "") {
                mp = MediaPlayer.create(this@Countries, resources.getIdentifier(sound2.tag as String, "raw", packageName))
                mp.start()
                val alert= Dialog(this@Countries)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)
                title.text="The Field is empty"
                alert.show()

                return@setOnClickListener
            } else if (sumedh.text != name2) {
                mp = MediaPlayer.create(this@Countries, resources.getIdentifier(wrong5.tag as String, "raw", packageName))
                mp.start()
                val alert= Dialog(this@Countries)
                alert.setContentView(R.layout.alltexts)
                val title=alert.findViewById<TextView>(R.id.alltext)

                title.text="Wrong Answer"
                alert.show()
                return@setOnClickListener
            }
            else if(d==items.size-1) {
                val alert = Dialog(this@Countries)
                alert.setContentView(R.layout.allcompleted)
                val next = alert.findViewById<Button>(R.id.button2)
                next.setOnClickListener {
                    startActivity(Intent(this, options::class.java))
                }
            }
            else  {
                mp = MediaPlayer.create(
                    this@Countries,
                    resources.getIdentifier(right5.tag as String, "raw", packageName)
                )
                mp.start()


                val alert2 = Dialog(this@Countries)
                alert2.setContentView(R.layout.capital)
                val yes2 = alert2.findViewById<Button>(R.id.yes2)
                val no2 = alert2.findViewById<Button>(R.id.no2)
                val speak=alert2.findViewById<ImageButton>(R.id.pop_speak_capital)
                alert2.show()
                speak.setOnClickListener {
                    Toast.makeText(applicationContext, "Wait a moment", Toast.LENGTH_SHORT).show()
                    text2speech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
                        if (status == TextToSpeech.SUCCESS)
                        {
                            text2speech.language = Locale.US
                            text2speech.speak(name2, TextToSpeech.QUEUE_FLUSH, null, null)
                        }

                    })
                }

                no2.setOnClickListener {
                       alert2.dismiss()
                    val DB = SQHelpercountries(applicationContext)
                    val word_input = items[d].trim()
                    val meaning_input = items2[d]
                    DB.ADD_DATA(word_input, meaning_input)
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
                                val treasurescore = getSharedPreferences("trs1",MODE_PRIVATE)
                                val editor6 = treasurescore.edit()
                                editor6.putInt("scro1", i)
                                editor6.commit()
                            }
                        }
                        val alert= Dialog(this@Countries)
                        alert.setContentView(R.layout.levelcompleted)
                        val nxtlevel=alert.findViewById<Button>(R.id.next_level)
                        alert.show()
                        nxtlevel.setOnClickListener {
//                            if(a>5&&n==0)
//                            {
//
//                                if (mInterstitialAd.isLoaded) {
//                                    mInterstitialAd.show()
//                                    n++
//                                }
//
//                            }
                            alert.dismiss()
                        }
                        var level_increase=getSharedPreferences("levcount", MODE_PRIVATE)
                        var editor3=level_increase.edit()
                        editor3.putInt("lnocount",a)
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

                    var myqno=getSharedPreferences("hellocount", MODE_PRIVATE)
                    var editor2=myqno.edit()
                    editor2.putInt("nocount",d)
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
                yes2.setOnClickListener {
                      alert2.dismiss()
                    val alert = Dialog(this@Countries)
                    alert.setContentView(R.layout.otheruse)
                    val title = alert.findViewById<TextView>(R.id.current_word2)
                    val desc = alert.findViewById<TextView>(R.id.meaning_word2)
                    val cont2 = alert.findViewById<Button>(R.id.cont2)
                    title.text = items[d]
                    desc.text = items2[d]
                    alert.show()
                    cont2.setOnClickListener {
                        alert.dismiss()
                        val DB = SQHelpercountries(applicationContext)
                        val word_input = items[d].trim()
                        val meaning_input = items2[d]
                        DB.ADD_DATA(word_input, meaning_input)
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
                                    val treasurescore = getSharedPreferences("trs1",MODE_PRIVATE)
                                    val editor6 = treasurescore.edit()
                                    editor6.putInt("scro1", i)
                                    editor6.commit()
                                }
                            }
                            val alert= Dialog(this@Countries)
                            alert.setContentView(R.layout.levelcompleted)
                            val nxtlevel=alert.findViewById<Button>(R.id.next_level)
                            alert.show()
                            nxtlevel.setOnClickListener {
//                                if(a>5&&n==0)
//                                {
//
//                                    if (mInterstitialAd.isLoaded) {
//                                        mInterstitialAd.show()
//                                        n++
//                                    }
//
//                                }
                                alert.dismiss()
                            }
                            var level_increase=getSharedPreferences("levcount", MODE_PRIVATE)
                            var editor3=level_increase.edit()
                            editor3.putInt("lnocount",a)
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

                        var myqno=getSharedPreferences("hellocount", MODE_PRIVATE)
                        var editor2=myqno.edit()
                        editor2.putInt("nocount",d)
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
        }





    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater2=menuInflater
        inflater2.inflate(R.menu.customfile,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {

            R.id.Words->{
                val intent7=Intent(this@Countries, actual_file::class.java)
                startActivity(intent7)
                return true
            }
            R.id.Countries->{
                val intent2=Intent(this@Countries, Countries::class.java)
                startActivity(intent2)
                return true
            }
            R.id.WildLife->{
                val intent3=Intent(this@Countries, WildLife::class.java)
                startActivity(intent3)
                return true
            }

            R.id.Vegetables->{
                val intent5=Intent(this@Countries, vegetables::class.java)
                startActivity(intent5)
                return true
            }
            R.id.Fruits->{
                val intent5=Intent(this@Countries, fruits::class.java)
                startActivity(intent5)
                return true

            }
        }


        return true

    }
    override fun onBackPressed() {
        startActivity(Intent(this@Countries,options::class.java))
        return
    }
    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-2474376873043059/4493838346",AdRequest.Builder().build())
    }
    private fun takewords() {
        var myqno=getSharedPreferences("hellocount", MODE_PRIVATE)
        d=myqno.getInt("nocount",0)
        var t= items[d].toString().trim()
        name2=items[d]
        first=items[d][0].toString()
        var editor=myqno.edit()
        editor.putInt("nocount",d)
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
//    fun prepareAd() {
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = "ca-app-pub-2474376873043059/1300698798"
//        mInterstitialAd.loadAd(AdRequest.Builder().build())
//    }
    fun changePos() {
        // Up
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
        Toast.makeText(baseContext, "Ad opened.", Toast.LENGTH_SHORT)
    }
    override fun onRewardedVideoAdOpened() {
        Toast.makeText(baseContext, "Ad opened.", Toast.LENGTH_SHORT).show()
    }
    override fun onRewardedVideoCompleted() {
        Toast.makeText(baseContext, "Ad completed.", Toast.LENGTH_SHORT).show()
    }


    override fun onRewarded(p0: RewardItem?) {
        val alert=Dialog(this@Countries)
        alert.setContentView(R.layout.coinsincrease)
        val claim=alert.findViewById<Button>(R.id.claim)
        alert.show()
        claim.setOnClickListener {
            startActivity(Intent(this,Countries::class.java))
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
        val shareBody = "What is the correct answer for the jumbled word '$sharetext2' ? hint(it's a name of a country)"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share Via :"))

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
                this@Countries,
                resources.getIdentifier(start4.tag as String, "raw", packageName)
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










