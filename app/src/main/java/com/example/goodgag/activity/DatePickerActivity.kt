package com.example.goodgag.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import kotlinx.android.synthetic.main.activity_datepicker.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*

public class DatePickerActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datepicker)

        btnSetBirth.setOnClickListener(){
            var intent = Intent(this,SignUpActivity::class.java).apply {
                putExtra("year",dpBirth.year)
                putExtra("month",dpBirth.month + 1)
                putExtra("day",dpBirth.dayOfMonth)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}