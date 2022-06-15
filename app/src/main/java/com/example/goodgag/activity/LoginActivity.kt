package com.example.goodgag.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        ///////////////////////////////////////////로그인 버튼
        btnLogin.setOnClickListener { Login(etID.text.toString(), etPassword.text.toString()) }

        ///////////////////////////////////////////회원가입 버튼
        btnSignUp.setOnClickListener{ startActivity(Intent(this, SignUpActivity::class.java)) }

        ///////////////////////////////////////////아이디비번 찾기 텍스트뷰
        tvFindPrivacy.setOnClickListener { startActivity(Intent(this,FindidpwActivity::class.java)) }
    }

    private fun Login(email : String, password : String) {
        var strID : String = etID.text.toString()
        var strPassword : String = etPassword.text.toString()

        //파이어베이스에서 로그인 아이디비번 가져와서 비교
        if(strID == "asd" && strPassword == "1") {
            Toast.makeText(this, "로그인 완료", Toast.LENGTH_LONG).show()
            finish()
        }
        //로그인 완료시 finish()되고 settings.xml에서 로그인아이디로 변경
        else {
            tvLoginError.visibility = View.VISIBLE
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
        }

    }
}
