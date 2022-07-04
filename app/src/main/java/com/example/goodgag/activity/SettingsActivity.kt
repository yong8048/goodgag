package com.example.goodgag.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.util.Log
import android.view.View.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
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
                UserManager.getinstance(this).clearUser()
                Initialize()
            }
        }
        
        //임시
        tvUserInfo.setOnClickListener{
            if(auth!!.currentUser != null){
                startActivity(Intent(this,MyPageActivity::class.java))
            }
        }

        swPushSetting.setOnClickListener { }

        swDarkMode.setOnCheckedChangeListener{ _, checked ->
            if(checked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        tvUploadPost.setOnClickListener{
            // 저장소 권한
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(this,UploadPostActivity::class.java))
            }
            else{
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        swDarkMode.isChecked = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
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

        swDarkMode.isChecked = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

        tvVersion.text = getVersion(this)

        var permission : Boolean? = UserManager.getinstance(this@SettingsActivity).Permission
        if(permission == true){
            tvUploadPost.visibility = VISIBLE
        }
        else{
            tvUploadPost.visibility = INVISIBLE
        }
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
