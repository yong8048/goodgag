package com.example.goodgag.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.goodgag.R
import com.example.goodgag.activity.MainActivity
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

class SplashActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    // After 3000 mileSeconds / 3 seconds your next activity will display.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = Firebase.auth
        database = Firebase.database.reference
        loadSplashScreen()
        Initialize()
    }

    private fun loadSplashScreen() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }
    private fun Initialize(){
        if (auth!!.currentUser != null) {
        var _email: String = auth!!.currentUser?.email.toString()
        var email: StringBuilder = StringBuilder().append(_email).deleteCharAt(_email.length - 4)
        val userData = Array<String>(5){""}
        var index: Int = 0
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.child("USERS/user_${email.toString()}")
                for(_data in data.children){
                    userData[index++] = _data.value.toString()
                }
                UserManager.getinstance(this@SplashActivity).registUser(userData[0],userData[1],userData[2],userData[3],userData[4])
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }

    }
}
