package com.example.goodgag.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var mailData = resources.getStringArray(R.array.mails)
        spMailList.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mailData)


    }
}