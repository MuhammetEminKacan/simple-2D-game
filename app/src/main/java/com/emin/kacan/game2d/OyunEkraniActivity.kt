package com.emin.kacan.game2d

import android.annotation.SuppressLint
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.emin.kacan.game2d.databinding.ActivityOyunEkraniBinding
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.math.floor

class OyunEkraniActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOyunEkraniBinding
    private var anakarakterX = 0.0f
    private var anakarakterY = 0.0f
    private var yesilYuvarlakX = 0.0f
    private var yesilYuvarlakY = 0.0f
    private var turuncuYuvarlakX = 0.0f
    private var turuncuYuvarlakY = 0.0f
    private var siyahYuvarlakX = 0.0f
    private var siyahYuvarlakY = 0.0f
    private var ekranGenisligi = 0
    private var ekranYuksekligi = 0
    private var anaKarakterGenisligi = 0
    private var anaKarakterYuksekligi = 0
    private var dokunmaKontrol = false
    private var baslangicKontrol = false
    private val timer = Timer()
    private var skor = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOyunEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.siyahYuvarlak.x = -900.0f
        binding.siyahYuvarlak.y = -900.0f
        binding.turuncuYuvarlak.x = -900.0f
        binding.turuncuYuvarlak.y = -900.0f
        binding.yesilYuvarlak.x = -900.0f
        binding.yesilYuvarlak.y = -900.0f

        binding.cl.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (baslangicKontrol){
                    if (event?.action == MotionEvent.ACTION_DOWN){
                        Log.e("MotionEvent","ACTION_DOWN : ekrana dokundu")
                        dokunmaKontrol = true
                    }

                    if (event?.action == MotionEvent.ACTION_UP){
                        Log.e("MotionEvent","ACTION_UP : ekranı bıraktı")
                        dokunmaKontrol = false
                    }
                }else{
                    baslangicKontrol = true
                    binding.txtBaslamaYazisi.visibility = View.INVISIBLE
                    anakarakterX = binding.anaKarakter.x
                    anakarakterY = binding.anaKarakter.y

                    anaKarakterGenisligi = binding.anaKarakter.width
                    anaKarakterYuksekligi = binding.anaKarakter.height
                    ekranGenisligi = binding.cl.width
                    ekranYuksekligi = binding.cl.height

                    timer.schedule(0,20){
                        Handler(Looper.getMainLooper()).post {
                            anaKarakterHareketEttirme()
                            cisimHareketEttirme()
                            carpismaKontrol()
                        }
                    }
                }

                return true
            }

        })

    }
    fun anaKarakterHareketEttirme(){
        val anaKarakterHiz = ekranYuksekligi/80.0f
        if (dokunmaKontrol){
            anakarakterY -= anaKarakterHiz
        }else{
            anakarakterY += anaKarakterHiz
        }

        if (anakarakterY <= 0.0f){
            anakarakterY = 0.0f
        }

        if (anakarakterY >= ekranYuksekligi - anaKarakterYuksekligi){
            anakarakterY = (ekranYuksekligi - anaKarakterYuksekligi).toFloat()
        }

        binding.anaKarakter.y = anakarakterY

    }

    fun cisimHareketEttirme(){
        siyahYuvarlakX-=ekranGenisligi/44.0f
        turuncuYuvarlakX-=ekranGenisligi/54.0f
        yesilYuvarlakX-=ekranGenisligi/36.0f

        if (siyahYuvarlakX <= 0.0f){
            siyahYuvarlakX = ekranGenisligi+20.0f
            siyahYuvarlakY = floor(Math.random()*ekranYuksekligi).toFloat()
        }

        binding.siyahYuvarlak.x = siyahYuvarlakX
        binding.siyahYuvarlak.y = siyahYuvarlakY

        if (turuncuYuvarlakX <= 0.0f){
            turuncuYuvarlakX = ekranGenisligi+20.0f
            turuncuYuvarlakY = floor(Math.random()*ekranYuksekligi).toFloat()
        }

        binding.turuncuYuvarlak.x = turuncuYuvarlakX
        binding.turuncuYuvarlak.y = turuncuYuvarlakY

        if (yesilYuvarlakX <= 0.0f){
            yesilYuvarlakX = ekranGenisligi+20.0f
            yesilYuvarlakY = floor(Math.random()*ekranYuksekligi).toFloat()
        }

        binding.yesilYuvarlak.x = yesilYuvarlakX
        binding.yesilYuvarlak.y = yesilYuvarlakY
    }

    fun carpismaKontrol(){
        val yesilDaireMerkezX = yesilYuvarlakX + binding.yesilYuvarlak.width/2.0f
        val yesilDaireMerkezY = yesilYuvarlakY + binding.yesilYuvarlak.height/2.0f

        if(0.0f <= yesilDaireMerkezX && yesilDaireMerkezX <= anaKarakterGenisligi
            && anakarakterY <= yesilDaireMerkezY && yesilDaireMerkezY <= anakarakterY+anaKarakterYuksekligi ){
            skor+=50
            yesilYuvarlakX = -10.0f
        }

        val turuncuDaireMerkezX = turuncuYuvarlakX + binding.turuncuYuvarlak.width/2.0f
        val turuncuDaireMerkezY = turuncuYuvarlakY + binding.turuncuYuvarlak.height/2.0f

        if(0.0f <= turuncuDaireMerkezX && turuncuDaireMerkezX <= anaKarakterGenisligi
            && anakarakterY <= turuncuDaireMerkezY && turuncuDaireMerkezY <= anakarakterY+anaKarakterYuksekligi ){
            skor+=20
            turuncuYuvarlakX = -10.0f
        }

        val siyahDaireMerkezX = siyahYuvarlakX + binding.siyahYuvarlak.width/2.0f
        val siyahDaireMerkezY = siyahYuvarlakY + binding.siyahYuvarlak.height/2.0f

        if(0.0f <= siyahDaireMerkezX && siyahDaireMerkezX <= anaKarakterGenisligi
            && anakarakterY <= siyahDaireMerkezY && siyahDaireMerkezY <= anakarakterY+anaKarakterYuksekligi ){
            turuncuYuvarlakX = -10.0f

            timer.cancel()
            val intent = Intent(this@OyunEkraniActivity,SonucEkranActivity::class.java)
            intent.putExtra("skor",skor)
            startActivity(intent)
            finish()
        }
        binding.txtSkor.text = skor.toString()
    }
}