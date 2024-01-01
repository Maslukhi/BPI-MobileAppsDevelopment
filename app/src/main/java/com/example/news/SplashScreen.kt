package com.example.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val background = object : Thread(){
            override fun run(){
                try {
                    Thread.sleep(3000)
                    val intent =Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}