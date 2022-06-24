package com.example.goodgag.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_findidpw.*

class FindidpwActivity : AppCompatActivity(){

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findidpw)

        //////////////////////////////////////// btnFindID 클릭
        btnFindID.setOnClickListener { Click_btnFindID(it) }

        //////////////////////////////////////// btnFindPW 클릭
        btnFindPW.setOnClickListener { Click_btnFindPW(it) }
    }
    private fun Click_btnFindID(view: View){
        var FindPhone : String = etFindIDPhone.text.toString()
        var email : StringBuilder = StringBuilder()

        database.reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var data = snapshot.child("USERS")
                for(_data in data.children){
                    for(__data in _data.children){
                        if(__data.value.toString() == FindPhone){
                            break
                        }
                    }
                    email.append(_data.key.toString())
                }
                data = snapshot.child("USERS/${email.toString()}")
                var index : Int = 0
                val userData = Array<String>(5){""}
                for(_data in data.children){
                    userData[index++] = _data.value.toString()
                }
                var userPhone = userData[SignUpActivity.SignUpInfo.PHONENUMBER.num].toString()
                var userName = userData[SignUpActivity.SignUpInfo.NAME.num].toString()
                email.delete(0, 5)

                if(userPhone.toString().equals(etFindIDPhone.text.toString()) && userName.toString().equals(etFindIDName.text.toString()))
                    Toast.makeText(this@FindidpwActivity, "아이디찾기 성공\n${email.insert(email.length -3, ".").toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) { }
        })

    }
    private  fun Click_btnFindPW(view: View){
        var email : StringBuilder = StringBuilder().append(etFindPWEmail.text.toString())
        email.deleteCharAt(email.length- 4)
        val userData = Array<String>(5) { "" }
        var index: Int = 0

        database.reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var data = snapshot.child("USERS/user_${email.toString()}")
                for (_data in data.children) {
                    userData[index++] = _data.value.toString()
                }
                var userPhone = userData[SignUpActivity.SignUpInfo.PHONENUMBER.num].toString()
                var userName = userData[SignUpActivity.SignUpInfo.NAME.num].toString()

                if (userPhone.toString().equals(etFindPWPhone.text.toString())
                    && userName.toString().equals(etFindPWName.text.toString())) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(etFindPWEmail.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this@FindidpwActivity,"비밀번호 변경 메일을 전송했습니다", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this@FindidpwActivity,"정보가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }
}