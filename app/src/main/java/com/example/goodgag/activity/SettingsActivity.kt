package com.example.goodgag.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.example.goodgag.Singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(){

    var auth : FirebaseAuth? = null

    private lateinit var ResLogin : ActivityResultLauncher<Intent>
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = Firebase.auth
        database = Firebase.database.reference
        Initialize()

        ResLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val nickname = it.data?.getStringExtra(SignUpActivity.SignUpInfo.NICKNAME.toString())!!
                tvUserInfo.text = nickname.toString()
                tvLogin.text = "로그아웃"
//                database.child()
                var userInfo : com.example.goodgag.data.UserInfo = com.example.goodgag.data.UserInfo(
                    uName = "", uNickname = "", uEmail = "", uPhonenumber = "",uBirthday = "")
                val sin = Singleton.getInstance(userInfo)
            }
        }

        tvLogin.setOnClickListener {
            if(auth!!.currentUser == null) {
                var intent = Intent(this, LoginActivity::class.java)
                ResLogin.launch(intent)
            }
            else{
                auth!!.signOut()
                Initialize()
            }
        }

        swPushSetting.setOnClickListener { }
    }

    private fun Initialize(){
        if (auth!!.currentUser == null) {
            tvUserInfo.text = ""
            tvLogin.text = "로그인"
        }
        else {
            var _email : String = auth!!.currentUser?.email.toString()
            var email : StringBuilder = StringBuilder().append(_email).deleteCharAt(_email.length - 4)
            database.child("user_$email").child(SignUpActivity.SignUpInfo.NICKNAME.toString()).get()
                .addOnSuccessListener {
                    tvUserInfo.text = it.value.toString()
                    tvLogin.text = "로그아웃"
                }
        }
        tvVersion.text = getVersion(this)
    }

    private fun getVersion(context: Context) : String{
        var versionName = ""
        try{
            val pm = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = pm.versionName
        }
        catch (e: Exception){
            Toast.makeText(this,"Exception, 수정필요\n ${e.toString()}",Toast.LENGTH_SHORT).show()
        }

        return versionName
    }
}
