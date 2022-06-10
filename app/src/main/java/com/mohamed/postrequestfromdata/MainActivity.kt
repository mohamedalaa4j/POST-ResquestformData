package com.mohamed.postrequestfromdata

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mohamed.postrequestfromdata.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null

    private var imageData: ByteArray? = null

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnSelectImage?.setOnClickListener { launchGallery() }
        binding?.btnUploadImage?.setOnClickListener { register() }
    }

    private fun register() {
        imageData?: return
        val queue = Volley.newRequestQueue(this)

        val url = "https://api.est3lamy.com/api/User/Register"

         //region StringRequest
        val jsonObjectRequest = object : StringRequest(
            Method.POST, url, Response.Listener { response ->


                Log.e("api response", response.toString())
                Toast.makeText(applicationContext, "Logged In Successfully ", Toast.LENGTH_SHORT).show()

            },
            { error ->

                Log.e("api response", error.toString())
                Toast.makeText(applicationContext, "Error getting the data! ", Toast.LENGTH_LONG).show()

                val errorResponse = error.networkResponse

                if (errorResponse?.data != null) {
                    val errorResponseJsonBody = String(errorResponse.data)
                    Log.e("error", errorResponseJsonBody)
                }
            }) {


            // form-date  >>>> POST - [ Key | value ]
            override fun getParams(): MutableMap<String?, String?> {
                val params: MutableMap<String?, String?> = HashMap()
                params["fullName"] = "Ahmed Samir"
                params["email"] = "ahmedsamir@yahoo.com"
                params["phoneNumber"] = "01092517066"
                params["cashNumber"] = "01280160444"
                params["city"] = "Gharbia"
                params["education"] = "Bachelor"
                params["address"] = "elmahalla"
                params["addressDetails"] = "20 ahmed st."
                params["nationalId"] = "151418191756"
                params["coverageArea"] = "3";"4"
                params["companyId"] = "3"
                params["password"] = "Aa_111111"
                params["ConfirmPassword"] = "Aa_111111"
                params["someOneFullName"] = "Ali Ahmed"
                params["someOnePhoneNumber"] = "01252465879"
                params["someOneRelationShip"] = "brother"
                params["someOneDescription"] = "big"
                return params
            }


            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }



        }
        //endregion
        //region VolleyFileUploadRequest
        val VolleyFileUploadRequest = object : VolleyFileUploadRequest(
            Method.POST, url, Response.Listener { response ->


                Log.e("api response", response.toString())
                Toast.makeText(applicationContext, "Logged In Successfully ", Toast.LENGTH_SHORT).show()

            },
            { error ->

                Log.e("api response", error.toString())
                Toast.makeText(applicationContext, "Error getting the data! ", Toast.LENGTH_LONG).show()

                val errorResponse = error.networkResponse

                if (errorResponse?.data != null) {
                    val errorResponseJsonBody = String(errorResponse.data)
                    Log.e("error", errorResponseJsonBody)
                }
            }) {


            // form-date  >>>> POST - [ Key | value ]
            override fun getParams(): MutableMap<String?, String?> {
                val params: MutableMap<String?, String?> = HashMap()
                params["fullName"] = "test volley5"
                params["email"] = "testvolley5@yahoo.com"
                params["phoneNumber"] = "01755555555"
                params["cashNumber"] = "01280160444"
                params["city"] = "Gharbia"
                params["education"] = "Bachelor"
                params["address"] = "elmahalla"
                params["addressDetails"] = "20 ahmed st."
                params["nationalId"] = "151418191756"
                params["coverageArea"] = "3";"4"
                params["companyId"] = "3"
                params["password"] = "Aa_111111"
                params["ConfirmPassword"] = "Aa_111111"
                params["someOneFullName"] = "Ali Ahmed"
                params["someOnePhoneNumber"] = "01252465879"
                params["someOneRelationShip"] = "brother"
                params["someOneDescription"] = "big"
                return params
            }


//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): MutableMap<String, String> {
//                val params: MutableMap<String, String> = HashMap()
//              //  params["Content-Type"] = "application/x-www-form-urlencoded"
//                // params["Content-Type"] = "multipart/x-form-data"
//                return params
//            }



            override fun getByteData(): MutableMap<String, FileDataPart> {
                val params = HashMap<String, FileDataPart>()
                params["profileImage"] = FileDataPart("profileImage", imageData!!, "png")
                params["nationalIdFront"] = FileDataPart("nationalIdFront", imageData!!, "png")
                params["nationalIdBack"] = FileDataPart("nationalIdBack", imageData!!, "png")
                params["criminalFish"] = FileDataPart("criminalFish", imageData!!, "png")
                params["academicQualification"] = FileDataPart("academicQualification", imageData!!, "png")
                return params
            }

        }
        //endregion

       // queue.add(jsonObjectRequest)
         Volley.newRequestQueue(this).add(VolleyFileUploadRequest)

    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data
            if (uri != null) {
                binding?.iv?.setImageURI(uri)
                createImageData(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}