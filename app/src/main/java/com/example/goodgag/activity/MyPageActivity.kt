package com.example.goodgag.activity

import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.example.goodgag.user.UserManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_signup.*

class MyPageActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        auth = Firebase.auth
        Initialize()

        btnMypagePwCheck.setOnClickListener {
            var Email: String = etMypageEmail.text.toString()
            var password = etMypagePW.text.toString()
            if (Email == "" || password == "") {
                Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
            }
            else {
                auth!!.signInWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                            etMypageNickname.isEnabled = true
                            etMypagePhone.isEnabled = true
                            btnMypageModify.visibility = VISIBLE
                            btnMypageModify.isEnabled = true
                        }
                        else{
                            Toast.makeText(this,"Password Fail",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        btnMypageModify.setOnClickListener{
            var email : StringBuilder = StringBuilder().append(etMypageEmail.text.toString())
            email.deleteCharAt(email.length - 4)

            if(UserManager.getinstance(this).Nickname != etMypageNickname.text.toString()){
                database.getReference("USERS/user_$email" + "/${SignUpActivity.SignUpInfo.NICKNAME.toString()}").setValue(etMypageNickname.text.toString())
            }
            if(UserManager.getinstance(this).Phonenumber != etMypagePhone.text.toString()){
                database.getReference("USERS/user_$email" + "/${SignUpActivity.SignUpInfo.PHONENUMBER.toString()}").setValue(etMypagePhone.text.toString())
            }
            finish()
        }
    }

    private fun Initialize(){
        etMypageBirth.setText(UserManager.getinstance(this).Birthday)
        etMypageNickname.setText(UserManager.getinstance(this).Nickname)
        etMypageName.setText(UserManager.getinstance(this).Name)
        etMypagePhone.setText(UserManager.getinstance(this).Phonenumber)
        etMypageEmail.setText(UserManager.getinstance(this).Email)
    }
    private fun tostring (edittext : EditText) : String{
        return edittext.text.toString()
    }
}