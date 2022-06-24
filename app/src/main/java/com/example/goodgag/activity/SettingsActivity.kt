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

    private lateinit var ResLogin: ActivityResultLauncher<Intent>
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = Firebase.auth
        database = Firebase.database.reference
        Initialize()

        ResLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val nickname =
                    it.data?.getStringExtra(SignUpActivity.SignUpInfo.NICKNAME.toString())!!
                tvUserInfo.text = nickname.toString()
                tvLogin.text = "로그아웃"

                var _email: String = auth!!.currentUser?.email.toString()
                var email: StringBuilder =
                    StringBuilder().append(_email).deleteCharAt(_email.length - 4)

                val userData = Array<String>(5){""}
                var index: Int = 0
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data = snapshot.child("USERS/user_$email")
                        for (_data in data.children) {
                            userData[index++] = _data.value.toString()
                        }
                        val userInfo = UserManager.getinstance(this@SettingsActivity)
                        userInfo.registUser(userData[0],userData[1],userData[2],userData[3],userData[4])
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("snap Error", "Error")
                    }
                })

            }


        }
        tvLogin.setOnClickListener {
            if (auth!!.currentUser == null) {
                var intent = Intent(this, LoginActivity::class.java)
                ResLogin.launch(intent)
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
