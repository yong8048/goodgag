package com.example.goodgag.activity

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.goodgag.R
import kotlinx.android.synthetic.main.activity_fileupload.*
import kotlinx.android.synthetic.main.activity_main.*

class UploadPostActivity : AppCompatActivity(){

    private lateinit var getImageResult : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fileupload)

        getImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                if(it.data?.clipData != null){
                    Log.i("getImage","${it.data.toString()},    ${it.data?.clipData!!.itemCount}")
                    for(i in 0 until it.data?.clipData!!.itemCount){
                        val imageview = ImageView(this)
                        imageview.scaleType = ImageView.ScaleType.FIT_CENTER
                        llUploadPost.addView(imageview)

                        var uriPhoto : ClipData.Item? = it.data?.clipData!!.getItemAt(i)
                        imageview.setImageURI(uriPhoto?.uri)
                        Log.i("getImage","${it.data.toString()},    ${it.data?.clipData!!.getItemAt(i).toString()},   ${i.toString()}")
                    }
                }
                else{
                    it.data?.data?.let { uri ->
                        val imageURI  :  Uri? = it.data?.data
                        if(imageURI != null){
                            val imageview = ImageView(this)
                            imageview.scaleType = ImageView.ScaleType.FIT_CENTER
                            llUploadPost.addView(imageview)

                            Glide.with(this).load(imageURI).into(imageview)
//                            imageview.setImageURI(imageURI)
                        }
                    }
                }

            }
        }

        btnOpenGallery.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            getImageResult.launch(intent)
        }

    }
}