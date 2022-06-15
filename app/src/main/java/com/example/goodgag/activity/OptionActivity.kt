package com.example.goodgag.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import kotlinx.android.synthetic.main.activity_settings.*

class OptionActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        swPushSetting.setOnClickListener {

        }

    }
}