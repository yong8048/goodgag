package com.example.goodgag.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.example.goodgag.adapter.SignUpSpinnerAdapter
import com.google.firebase.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

//        var sData = resources.getStringArray(R.array.mails)
//        var adapter = ArrayAdapter<String>(this,R.layout.activity_signup,sData)
//        spMailList.adapter = adapter

//        spMailList.setSelection(1)
    }
}