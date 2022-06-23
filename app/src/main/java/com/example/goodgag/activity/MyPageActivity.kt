package com.example.goodgag.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.example.goodgag.user.UserManager
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_signup.*

class MyPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        Initialize()


    }

    private fun Initialize(){
//        for(data in )
        etMypageBirth.setText(UserManager.getinstance(this).Birthday)
        etNickname.setText(UserManager.getinstance(this).Nickname)
        etMypageName.setText(UserManager.getinstance(this).Name)
        etMypagePhone.setText(UserManager.getinstance(this).Phonenumber)
        etMypageEmail.setText(UserManager.getinstance(this).Email)

    }
}