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
    var bLogincheck : Boolean = false
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
        if(etID.text.toString() == "" || etPassword.text.toString() == ""){
            tvLoginError.visibility = View.VISIBLE
            Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
        } else {
            auth!!.signInWithEmailAndPassword(etID.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                        val user = auth?.currentUser
                        bLogincheck = true
                        finish()
                    } else {
                        tvLoginError.visibility = View.VISIBLE
                        Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
