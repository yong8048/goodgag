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
        firebasepath!!.createUserWithEmailAndPassword(et_email.text.toString().trim() + spMailList.selectedItem.toString().trim(), et_pw.text.toString().trim()).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                val user = firebasepath?.currentUser
                Toast.makeText(this, "Authentication Success.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Authentication Fail.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}