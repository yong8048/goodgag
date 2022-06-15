package com.example.goodgag.activity

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import kotlinx.android.synthetic.main.activity_settings.*

class OptionActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvLogin.setOnClickListener {
            var intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        swPushSetting.setOnClickListener {
            var intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}