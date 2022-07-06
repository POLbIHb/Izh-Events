package com.gmail.illobikol.vk_api_playbill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.gmail.illobikol.vk_api_playbill.R

class SplashScreen : AppCompatActivity() {
    private  lateinit var  mIvIzhevent: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        mIvIzhevent = findViewById(R.id.iv_izhevent)
        mIvIzhevent.alpha =0f
        mIvIzhevent.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}