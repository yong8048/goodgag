package com.example.goodgag.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodgag.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    var firebasepath: FirebaseAuth? = null

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
        var toastMessage: String = ""
        if(etName.text.toString() == ""){
            toastMessage += "Name, "
        }
        if (etEmail.text.toString() == "") {
            toastMessage += "Email, "
        }
        if(etPW.text.toString() == ""){
            toastMessage += "PassWord, "
        }
        if(etPWcheck.text.toString() == ""){
            toastMessage += "PassWord Check, "
        }
        if(etPhone1.text.toString() == "" || etPhone2.text.toString() == "" || etPhone3.text.toString() == ""){
            toastMessage += "Phone Number, "
        }
        if(etBirth.text.toString() == ""){
            toastMessage += "BirthDay, "
        }
        if(toastMessage == "") {
            firebasepath!!.createUserWithEmailAndPassword(
                etEmail.text.toString().trim() + spMailList.selectedItem.toString().trim(),
                etPW.text.toString().trim()
            ).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = firebasepath?.currentUser
                    toastMessage = "Authentication Success."
                }
                else {
                    toastMessage = "Authentication Fail!"
                }
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
        else{
            toastMessage = toastMessage.substring(0, toastMessage.length - 2)
            Toast.makeText(this, "Empty : $toastMessage", Toast.LENGTH_SHORT).show()
        }
    }
}