package com.example.goodgag.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
        val datebase : FirebaseDatabase = FirebaseDatabase.getInstance()
        var FindPhone : String = etFindIDPhone.text.toString()
        var userPhone = FirebaseDatabase.getInstance().getReference("user_" + FindPhone).child("PHONENUMBER")
        var userName = FirebaseDatabase.getInstance().getReference("user_"+FindPhone).child("NAME")
        var userEmail = FirebaseDatabase.getInstance().getReference("user_"+FindPhone).child("EMAIL")

//        if(userPhone.toString() == FindPhone && etFindIDName.text.toString() == userName.toString())
//            Toast.makeText(this, "아이디 찾기\n" + userEmail, Toast.LENGTH_LONG)

        if(userPhone.toString().equals(etFindIDPhone.text.toString()) && userName.toString().equals(etFindIDName.text.toString()))
            Toast.makeText(this, "아이디찾기 성공\n", Toast.LENGTH_SHORT)
    }
    private  fun Click_btnFindPW(view: View){

        FirebaseAuth.getInstance().sendPasswordResetEmail(etFindEmail.text.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                var userName = FirebaseDatabase.getInstance().getReference("user").child("name")
                var userBirth = FirebaseDatabase.getInstance().getReference("user").child("birth")
                Toast.makeText(this, "비밀번호 변경 메일을 전송했습니다", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}