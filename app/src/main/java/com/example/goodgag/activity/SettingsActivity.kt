package com.example.goodgag.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_signup.*

class SettingsActivity : AppCompatActivity(){

    var auth : FirebaseAuth? = null
    private lateinit var getResult : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = Firebase.auth
        Initialize()

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val email = it.data?.getStringExtra("email")!!
                tvUserInfo.text = email.toString()
                tvLogin.text = "로그아웃"
            }
        }

        tvLogin.setOnClickListener {
            if(auth!!.currentUser == null) {
                var intent = Intent(this, LoginActivity::class.java)
                getResult.launch(intent)
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
            tvUserInfo.text = auth!!.currentUser?.email.toString()
            tvLogin.text = "로그아웃"
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
