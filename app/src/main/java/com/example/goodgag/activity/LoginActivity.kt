package com.example.goodgag.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.goodgag.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_datepicker.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*


class LoginActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null
    var bLogincheck : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        ///////////////////////////////////////////로그인 버튼
        btnLogin.setOnClickListener {
            Login(etID.text.toString(), etPassword.text.toString())
            var intent = Intent(this, SettingsActivity::class.java).apply {
                putExtra("email", etID.text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
        }
        ///////////////////////////////////////////회원가입 버튼
        btnSignUp.setOnClickListener{ startActivity(Intent(this, SignUpActivity::class.java)) }

        ///////////////////////////////////////////아이디비번 찾기 텍스트뷰
        tvFindPrivacy.setOnClickListener { startActivity(Intent(this,FindidpwActivity::class.java)) }
    }

    private fun Login(email : String, password : String) {
        if(email == "" || password == ""){
            tvLoginError.visibility = View.VISIBLE
            Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
        } else {
            auth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                        val user = auth?.currentUser
//                        ViewUserInfo(email)
                        bLogincheck = true
                    } else {
                        tvLoginError.visibility = View.VISIBLE
                        Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun ViewUserInfo(email : String){

        tvUserInfo.text = email
        tvUserInfo.setTextColor(ContextCompat.getColor(this,R.color.black))
    }
}
