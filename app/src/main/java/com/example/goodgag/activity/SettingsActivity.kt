package com.example.goodgag.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        tvVersion.text = getVersion(this)


        tvLogin.setOnClickListener { startActivity(Intent(this,LoginActivity::class.java)) }


        swPushSetting.setOnClickListener { }

    }

    private fun getVersion(context: Context) : String{
        var versionName = ""
        try{
            val pm = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = pm.versionName
        }
        catch (e: Exception){
            Toast.makeText(this,"Exception, 수정필요",Toast.LENGTH_SHORT).show()
        }

        return versionName
    }
}
