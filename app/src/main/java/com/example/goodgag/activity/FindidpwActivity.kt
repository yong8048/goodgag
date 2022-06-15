package com.example.goodgag.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import kotlinx.android.synthetic.main.activity_findidpw.*

class FindidpwActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findidpw)

        //////////////////////////////////////// btnFindID 클릭
        btnFindID.setOnClickListener {
            Click_btnFindID(it)
        }

        //////////////////////////////////////// btnFindPW 클릭
        btnFindPW.setOnClickListener {
            Click_btnFindPW(it)
        }
    }
    private fun Click_btnFindID(view: View){

    }
    private  fun Click_btnFindPW(view: View){

    }
}