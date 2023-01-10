package com.app.newzpaper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val thread = Thread(){
            kotlin.run {
                try {
                    Thread.sleep(4000)
                }
                catch (e:Exception){
                    e.printStackTrace()

                }
                finally {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }

            }
        }
        thread.start()
    }
}