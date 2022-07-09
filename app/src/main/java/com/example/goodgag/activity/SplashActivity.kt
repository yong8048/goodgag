package com.example.goodgag.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.disklrucache.DiskLruCache
import com.example.goodgag.Post
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

class SplashActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference

    // After 3000 mileSeconds / 3 seconds your next activity will display.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = Firebase.auth
        database = Firebase.database.reference
        InitializeUserInfo()
        InitializePostInfo()
    }

    private fun StartMainGUI(postList: Array<Post?>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Post", postList)
        startActivity(intent)
        finish()
    }

    private fun InitializeUserInfo() {
        if (auth!!.currentUser != null) {
            var _email: String = auth!!.currentUser?.email.toString()
            var email: StringBuilder =
                StringBuilder().append(_email).deleteCharAt(_email.length - 4)
            val userData = Array<String>(6) { "" }
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.child("USERS/user_${email.toString()}")
                    var index: Int = 0
                    for (_data in data.children) {
                        userData[index++] = _data.value.toString()
                    }
                    UserManager.getinstance(this@SplashActivity).registUser(
                        userData[SignUpActivity.SignUpInfo.BIRTHDAY.num],
                        userData[SignUpActivity.SignUpInfo.EMAIL.num],
                        userData[SignUpActivity.SignUpInfo.NAME.num],
                        userData[SignUpActivity.SignUpInfo.NICKNAME.num],
                        userData[SignUpActivity.SignUpInfo.PHONENUMBER.num],
                        userData[SignUpActivity.SignUpInfo.PERMISSION.num].toBoolean()
                    )
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

    }

    private fun InitializePostInfo() {
        var postList = Array<Post?>(15) { null }
        var postIndex: Int = 0
        val intent = Intent(this, MainActivity::class.java)

        val databaseref = database.child("POSTNUMBER")

        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastPostNum: Int = snapshot.value.toString().toInt()
                var res: Int = 0
                for (_lastPostNum in lastPostNum downTo lastPostNum - 14) {
                    val postref = database.child("POST").child("POST_$_lastPostNum")
                    postref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var index: Int = 0
                            var _data = Array<String>(5) { "" }
                            for (data in snapshot.children) {
                                _data[index++] = data.value.toString()
                            }
                            val member = Post(_data[0], _data[1], _data[2], _data[3], _data[4])
                            postList[postIndex++] = member
                            if(postIndex == 15){
                                StartMainGUI(postList)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun _InitializePostInfo() {
        var postList = Array<Post?>(15) { null }
        var postIndex: Int = 0

        val databaseref = database.child("POSTNUMBER")

        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastPostNum: Int = snapshot.value.toString().toInt()
                var res: Int = 0
                val postref = database.child("POST")
                postref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var index: Int = 0
                        var _data = Array<String>(5) { "" }
                        for (data in snapshot.children) {
                            _data[index++] = data.value.toString()
                        }
                        val member = Post(_data[0], _data[1], _data[2], _data[3], _data[4])
                        postList[postIndex++] = member
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
