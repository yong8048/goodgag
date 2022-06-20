package com.example.goodgag.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import java.io.Serializable
import java.util.*


class SignUpActivity : AppCompatActivity() {
    enum class SignUpInfo {
        NAME,
        NICKNAME,
        EMAIL,
        PHONENUMBER,
        BIRTHDAY
    }
    var firebasepath : FirebaseAuth? = null
    var bSingUp : Boolean = true
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val namePattern = "^[ㄱ-ㅎ가-힣]{2,6}$"
    val pwPattern = "^[A-Za-z]{1,10}+[0-9]{1,20}$"
    val idPattern = "^[A-Za-z0-9]{1,10}$"
    val nicknamePattern = "^[ㄱ-ㅎ가-힣A-Za-z0-9]{2,8}$"
    val phonenumberPattern = "^\\d{3}-\\d{3,4}-\\d{4}$"

    var year : Int = 0
    var month : Int = 0
    var day : Int = 0

    private lateinit var getResult : ActivityResultLauncher<Intent>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var mailData = resources.getStringArray(R.array.mails)
        spMailList.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mailData)

        firebasepath = FirebaseAuth.getInstance()

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                year = it.data?.getIntExtra("year",0)!!
                month = it.data?.getIntExtra("month",0)!!
                day = it.data?.getIntExtra("day",0)!!

                tvBirth.text = "$year" + "년" + " $month" + "월" + " $day" + "일"
            }
        }

        tvBirth.setOnClickListener{
            var intent = Intent(this,DatePickerActivity::class.java)
            getResult.launch(intent)
        }
        btnSignUp.setOnClickListener { Click_SignUp(it) }
    }

    private fun Click_SignUp(view: View) {
        bSingUp = true
        if(etName.text.toString() == ""){
            Toast_SignUp("Name is empty", Toast.LENGTH_SHORT)
        }
        else if(!isFormatPattern(etName.text.toString(), namePattern)){
            Toast_SignUp("Name is not allow",Toast.LENGTH_SHORT)
        }
        else if(etNickname.text.toString() == ""){
            Toast_SignUp("Nickname is empty", Toast.LENGTH_SHORT)
        }
        else if(!isFormatPattern(etNickname.text.toString(), nicknamePattern)){
            Toast_SignUp("Nickname is not allow", Toast.LENGTH_SHORT)
        }
        else if (etEmail.text.toString() == "") {
            Toast_SignUp("Email is empty", Toast.LENGTH_SHORT)
        }
        else if(!isFormatPattern(etEmail.text.toString(),idPattern)){
            Toast_SignUp("Email is not allow", Toast.LENGTH_SHORT)
        }
        else if(etPW.text.toString() == "" || etPWcheck.text.toString() == ""){
            Toast_SignUp("PassWord is empty", Toast.LENGTH_SHORT)
        }
        else if(!isFormatPattern(etPW.text.toString(),pwPattern) || etPW.text.length < 6){
            Toast_SignUp("PassWord is not allow",Toast.LENGTH_SHORT)
        }
        else if(etPW.text.toString() != etPWcheck.text.toString()){
            Toast_SignUp("PassWord Mismatch", Toast.LENGTH_SHORT)
        }
        else if(etPhone1.text.toString() == "" || etPhone2.text.toString() == "" || etPhone3.text.toString() == ""){
            Toast_SignUp("Phone Number is empty", Toast.LENGTH_SHORT)
        }
        else if(etPhone1.text.toString().length != 3 || etPhone2.text.toString().toInt() < 100 || etPhone3.text.toString().toInt() < 1000){
            Toast_SignUp("Phone Number is not allow", Toast.LENGTH_SHORT)
        }
        else if(tvBirth.text.toString() == ""){
            Toast_SignUp("BirthDay is empty", Toast.LENGTH_SHORT)
        }
        if(bSingUp) {
            firebasepath!!.createUserWithEmailAndPassword(
                etEmail.text.toString().trim() + spMailList.selectedItem.toString().trim(),
                etPW.text.toString().trim()
            ).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = firebasepath?.currentUser
                    Toast_SignUp("Authentication Success", Toast.LENGTH_SHORT)
                    saveSignUpData()
                    finish()
                }
                else {
                    Toast_SignUp("Authentication Fail!", Toast.LENGTH_SHORT)
                }
            }
        }
    }
    private fun isFormatPattern(str: String, pattern : String): Boolean {
        return str.matches(pattern.toRegex())
    }

    // 회원가입정보 Firebase에 Upload
    private fun saveSignUpData(){
        val username : String = etName.text.toString()

        var phNumber = etPhone1.text.toString().plus(etPhone2.text.toString()).plus(etPhone3.text.toString())
        database.getReference("user_$phNumber" + "/${SignUpInfo.NAME}").setValue(etName.text.toString())
        database.getReference("user_$phNumber" + "/${SignUpInfo.NICKNAME}").setValue(etNickname.text.toString())
        database.getReference("user_$phNumber" + "/${SignUpInfo.EMAIL}").setValue(etEmail.text.toString() + spMailList.selectedItem.toString())
        database.getReference("user_$phNumber" + "/${SignUpInfo.PHONENUMBER}").setValue(phNumber.toString())
        database.getReference("user_$phNumber" + "/${SignUpInfo.BIRTHDAY}").setValue(tvBirth.text.toString())
    }

    private fun Toast_SignUp(message : String, length: Int){
        bSingUp = false
        Toast.makeText(this, message, length).show()
    }
}