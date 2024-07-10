 package com.emin.kacan.game2d

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emin.kacan.game2d.databinding.ActivitySonucEkranBinding

 class SonucEkranActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySonucEkranBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySonucEkranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val skor = intent.getIntExtra("skor",0)
        binding.txtScore.text = skor.toString()

        val sp = getSharedPreferences("com.emin.kacan.game2d",Context.MODE_PRIVATE)
        val enYuksekSkor = sp.getInt("enYuksekSkor",0)

        if (skor > enYuksekSkor){
            val editor = sp.edit()
            editor.putInt("enYuksekSkor",skor)
            editor.commit()
            binding.txtHighScore.text = skor.toString()
        }else{
            binding.txtHighScore.text = enYuksekSkor.toString()
        }

        binding.btnTekrar.setOnClickListener {
            startActivity(Intent(this@SonucEkranActivity,MainActivity::class.java))
        }
    }
}