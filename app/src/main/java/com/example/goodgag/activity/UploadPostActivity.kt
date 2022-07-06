package com.example.goodgag.activity

import android.app.ActionBar
import android.app.AlertDialog
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.goodgag.R
import com.example.goodgag.user.UserManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_fileupload.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.LocalDateTime

class UploadPostActivity : AppCompatActivity(){
    enum class POSTINFO (val num : Int) {
        DATE(0),
        HEADER(1),
        NUMBER(2),
        SOURCE(3),
        UPLOADER(4),
    }

    private lateinit var getImageResult : ActivityResultLauncher<Intent>
    var storage : FirebaseStorage = FirebaseStorage.getInstance()
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fileupload)
        var Imagelist = ArrayList<Uri?>()

        tvUploader.text = UserManager.getinstance(this).Nickname

        getImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                if(it.data?.clipData != null){
                    for(i in 0 until it.data?.clipData!!.itemCount){
                        val imageview = ImageView(this)
                        imageview.scaleType = ImageView.ScaleType.FIT_CENTER
                        imageview.layoutParams = ViewGroup.LayoutParams(300,300)
                        llUploadPost.addView(imageview)

                        var imageURI : ClipData.Item? = it.data?.clipData!!.getItemAt(i)
                        Imagelist.add(imageURI!!.uri)
                        Glide.with(this).load(imageURI.uri).into(imageview)
                    }
                }
                else{
                    it.data?.data?.let { uri ->
                        val imageURI  :  Uri? = it.data?.data
                        if(imageURI != null){
                            val imageview = ImageView(this)
                            imageview.scaleType = ImageView.ScaleType.FIT_CENTER
                            imageview.layoutParams = ViewGroup.LayoutParams(300,300)
                            llUploadPost.addView(imageview)

                            Imagelist.add(imageURI)
                            Glide.with(this).load(imageURI).into(imageview)
                        }
                    }
                }

            }
        }

        btnGetImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            getImageResult.launch(intent)
        }

        btnGetVideo.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("video/*")
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            getImageResult.launch(intent)
        }


        btnUploadPost.setOnClickListener{
            if(Imagelist.isEmpty()){
                Short_Toast("파일이 없습니다")
            }
            else if(etUploadHeader.text.isEmpty()){
                Short_Toast("제목을 입력하세요")
            }
            else if(etUploadSource.text.isEmpty()){
                Short_Toast("출처를 입력하세요")
            }
            else{
                database.reference.child("POSTNUMBER").get().addOnSuccessListener {
                    val postNumber = it.value.toString().toInt() + 1

                    var storageref = storage.reference.child("$postNumber")
                    for (data in Imagelist) {
                        var imgFilename = LocalDateTime.now().toString()
                        storageref.child(imgFilename).putFile(data!!)
                    }

                    val databasepath : String = "POST/POST_${postNumber.toString()}"
                    database.getReference("$databasepath/${POSTINFO.DATE.toString()}").setValue("${LocalDate.now()}")
                    database.getReference("$databasepath/${POSTINFO.HEADER.toString()}").setValue("${etUploadHeader.text.toString()}")
                    database.getReference("$databasepath/${POSTINFO.NUMBER.toString()}").setValue("$postNumber")
                    database.getReference("$databasepath/${POSTINFO.SOURCE.toString()}").setValue("${etUploadSource.text.toString()}")
                    database.getReference("$databasepath/${POSTINFO.UPLOADER.toString()}").setValue("${UserManager.getinstance(this).Nickname}")
                    database.getReference("POSTNUMBER").setValue(postNumber)
                    Short_Toast("업로드 성공!!")
                }
                finish()
            }
        }

        btnUploadListClear.setOnClickListener{
//            Imagelist.clear()
//            llUploadPost.removeAllViews()
            for(data in Imagelist){
                Log.i("DataType","${data.toString()}")
            }
        }

    }

    private fun Short_Toast(message : String){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }
}