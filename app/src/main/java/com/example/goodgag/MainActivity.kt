package com.example.goodgag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnBack = findViewById<Button>(R.id.btnBack);

        btnBack.setOnClickListener{
            x();
        }
    }

    fun x(){
    }
}