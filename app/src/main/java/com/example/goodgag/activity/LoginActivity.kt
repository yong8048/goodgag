package com.example.goodgag.activity

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.renderscript.Sampler
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.goodgag.R
import com.example.goodgag.user.UserManager
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
import java.util.concurrent.Delayed


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
            var Email : String = etID.text.toString()
            var password = etPassword.text.toString()
            if (Email == "" || password == "") {
                tvLoginError.visibility = View.VISIBLE
                Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
            }
            else {
                auth!!.signInWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            var email: StringBuilder = StringBuilder().append(etID.text.toString())
                                .deleteCharAt(etID.text.toString().length - 4)
                            val userData = Array<String>(6) { "" }
                            database.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val data = snapshot.child("USERS/user_$email")
                                    var index: Int = 0
                                    for (_data in data.children) {
                                        userData[index++] = _data.value.toString()
                                    }
                                    val userInfo = UserManager.getinstance(this@LoginActivity)
                                    userInfo.registUser(
                                        userData[SignUpActivity.SignUpInfo.BIRTHDAY.num],
                                        userData[SignUpActivity.SignUpInfo.EMAIL.num],
                                        userData[SignUpActivity.SignUpInfo.NAME.num],
                                        userData[SignUpActivity.SignUpInfo.NICKNAME.num],
                                        userData[SignUpActivity.SignUpInfo.PHONENUMBER.num],
                                        userData[SignUpActivity.SignUpInfo.PERMISSION.num].toBoolean()
                                    )
                                    Toast.makeText(this@LoginActivity, "Login success", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                override fun onCancelled(error: DatabaseError) { }
                            })
                        }
                        else {
                            tvLoginError.visibility = View.VISIBLE
                            Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

        ///////////////////////////////////////////회원가입 버튼
        btnSignUp.setOnClickListener{ startActivity(Intent(this, SignUpActivity::class.java)) }

        ///////////////////////////////////////////아이디비번 찾기 텍스트뷰
        tvFindPrivacy.setOnClickListener { startActivity(Intent(this,FindidpwActivity::class.java)) }
    }
}
