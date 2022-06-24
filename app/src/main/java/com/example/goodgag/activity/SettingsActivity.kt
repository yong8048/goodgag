package com.example.goodgag.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.example.goodgag.user.UserManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = Firebase.auth
        database = Firebase.database.reference
        Initialize()

        tvLogin.setOnClickListener {
            if (auth!!.currentUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                auth!!.signOut()
                Initialize()
                UserManager.getinstance(this).clearUser()
            }
        }
        
        //임시
        tvUserInfo.setOnClickListener{
            if(auth!!.currentUser != null){
                startActivity(Intent(this,MyPageActivity::class.java))
            }
        }

        swPushSetting.setOnClickListener { }
    }

    override fun onRestart() {
        super.onRestart()
        Initialize()
    }

    private fun Initialize() {
        if (auth!!.currentUser == null) {
            tvUserInfo.text = ""
            tvLogin.text = "로그인"
        } else {
            tvUserInfo.text = UserManager.getinstance(this@SettingsActivity).Nickname
            tvLogin.text = "로그아웃"
        }
        tvVersion.text = getVersion(this)
    }

    private fun getVersion(context: Context): String {
        var versionName = ""
        try {
            val pm = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = pm.versionName
        } catch (e: Exception) {
            Toast.makeText(this, "Exception, 수정필요\n ${e.toString()}", Toast.LENGTH_SHORT).show()
        }

        return versionName
    }
}
