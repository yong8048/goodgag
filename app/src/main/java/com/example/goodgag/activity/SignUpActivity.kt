package com.example.goodgag.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var mailData = resources.getStringArray(R.array.mails)
        spMailList.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mailData)

        firebasepath = FirebaseAuth.getInstance()


        btnSignUp.setOnClickListener {
            Click_SignUp(it)
        }
    }

    private fun Click_SignUp(view: View) {
        bSingUp = true
        if(etName.text.toString() == ""){
            Toast_SignUp("Name", Toast.LENGTH_SHORT)
        }
        else if (etEmail.text.toString() == "") {
            Toast_SignUp("Email", Toast.LENGTH_SHORT)
        }
        else if(etPW.text.toString() == "" || etPWcheck.text.toString() == ""){
            Toast_SignUp("PassWord", Toast.LENGTH_SHORT)
        }
        else if(etPhone1.text.toString() == "" || etPhone2.text.toString() == "" || etPhone3.text.toString() == ""){
            Toast_SignUp("Phone Number", Toast.LENGTH_SHORT)
        }
        else if(etBirth.text.toString() == ""){
            Toast_SignUp("BirthDay", Toast.LENGTH_SHORT)
        }
        else if(etPW.text.toString() != etPWcheck.text.toString()){
            Toast_SignUp("PassWord Mismatch", Toast.LENGTH_SHORT)
        }
        if(bSingUp) {
            firebasepath!!.createUserWithEmailAndPassword(
                etEmail.text.toString().trim() + spMailList.selectedItem.toString().trim(),
                etPW.text.toString().trim()
            ).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = firebasepath?.currentUser
                    Toast_SignUp("Authentication Success", Toast.LENGTH_SHORT, false)
                    saveSignUpData()
//                    getSignUpData()
//                    finish()
                }
                else {
                    Toast_SignUp("Authentication Fail!", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    // 회원가입정보 Firebase에 Upload
    private fun saveSignUpData(){
//        val pref = this.getPreferences(0)
//        val editor = pref.edit()
        var phNumber = etPhone1.text.toString().plus(etPhone2.text.toString()).plus(etPhone3.text.toString())
//
//        editor.putString(SignUpInfo.NAME.toString(), etName.text.toString())
//            .putString(SignUpInfo.PHONENUMBER.toString(), phNumber.toString())
//            .putInt(SignUpInfo.BIRTHDAY.toString(), etBirth.text.toString().toInt()).apply()

        val username : String = etName.text.toString()

//        val ref_username: DatabaseReference = database.getReference("user_$username" + "/name")
//        val ref_nickname: DatabaseReference = database.getReference("user_$username" + "/nickname")
//        val ref_email: DatabaseReference = database.getReference("user_$username" + "/email")
//        val ref_phonenumber: DatabaseReference = database.getReference("user_$username" + "/phonenumber")
//        val ref_birthday: DatabaseReference = database.getReference("user_$username" + "/birthday")
//
//        ref_username.setValue(etName.text.toString())
//        ref_nickname.setValue(etName.text.toString() + "_nickname")
//        ref_email.setValue(etEmail.text.toString())
//        ref_phonenumber.setValue(phNumber.toString())
//        ref_birthday.setValue(etBirth.text.toString())

        database.getReference("user_$username" + "/${SignUpInfo.NAME.toString()}").setValue(etName.text.toString())
        database.getReference("user_$username" + "/${SignUpInfo.NICKNAME.toString()}").setValue(etName.text.toString() + "_nickname")
        database.getReference("user_$username" + "/${SignUpInfo.EMAIL.toString()}").setValue(etEmail.text.toString() + spMailList.selectedItem.toString())
        database.getReference("user_$username" + "/${SignUpInfo.PHONENUMBER.toString()}").setValue(phNumber.toString())
        database.getReference("user_$username" + "/${SignUpInfo.BIRTHDAY.toString()}").setValue(etBirth.text.toString())
    }

    private fun getSignUpData(){
        val pref = this.getPreferences(0)
        val name = pref.getString(SignUpInfo.NAME.toString(), null)
        val phNumber = pref.getInt(SignUpInfo.PHONENUMBER.toString(), 0)
        val birth = pref.getInt(SignUpInfo.BIRTHDAY.toString(), 0)

        tvTest11.setText(name.toString())
        tvTest22.setText(phNumber.toString())
        tvTest33.setText(birth.toString())

    }

    private fun Toast_SignUp(str : String, length: Int, bError : Boolean = true){
        var msg = "Please Recheck $str"
        if(!bError){ msg = str }
        bSingUp = false

        Toast.makeText(this, msg, length).show()
    }
}