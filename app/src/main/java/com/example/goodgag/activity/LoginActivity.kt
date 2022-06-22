package com.example.goodgag.activity

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.renderscript.Sampler
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.goodgag.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_datepicker.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database


class LoginActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        database = Firebase.database.reference


        ///////////////////////////////////////////로그인 버튼
        btnLogin.setOnClickListener {
            if(Login(etID.text.toString(), etPassword.text.toString())){

                ///////////////////////////////////////// Login 후에 tvUserInfo에 Nickname 표시를 위해 SettingsActivity로 intent를 보낸다.
                var email : StringBuilder = StringBuilder().append(etID.text.toString()).deleteCharAt(etID.text.toString().length - 4)
                database.child("user_$email").child(SignUpActivity.SignUpInfo.NICKNAME.toString()).get().addOnSuccessListener {
                    val intent = Intent(this, SettingsActivity::class.java).apply {
                        putExtra(SignUpActivity.SignUpInfo.NICKNAME.toString(), it.value.toString())
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }



        }
        ///////////////////////////////////////////회원가입 버튼
        btnSignUp.setOnClickListener{ startActivity(Intent(this, SignUpActivity::class.java)) }

        ///////////////////////////////////////////아이디비번 찾기 텍스트뷰
        tvFindPrivacy.setOnClickListener { startActivity(Intent(this,FindidpwActivity::class.java)) }
    }


    private fun Login(email : String, password : String) : Boolean{
        var bLogincheck : Boolean = false
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
        return bLogincheck
    }
    private fun ViewUserInfo(email : String){

        tvUserInfo.text = email
        tvUserInfo.setTextColor(ContextCompat.getColor(this,R.color.black))
    }
}
