package com.example.taskaty.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.taskaty.data.api.UserApiClient
import com.example.taskaty.databinding.ActivityMainBinding
import com.example.taskaty.domain.entities.User
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val user=User("abcdaa","123456789","c2c02507-9968-486b-9bf0-4c3860fe1f2d")
        val okClient = OkHttpClient()
        val userClient = UserApiClient(okClient)
        userClient.signup(user,object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("xxxxf",e.toString())
            }

            override fun onResponse(call: Call, response: Response) {

                Log.d("xxxxs",response.isSuccessful.toString())
                Log.d("xxxxs",response.code.toString())
                Log.d("xxxxs",response.body.string())
            }
        })
        userClient.login(user.name,user.password,object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("aaaaf",e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("aaaas",response.isSuccessful.toString())
                Log.d("aaaas",response.code.toString())
                Log.d("aaaas",response.body.string())
            }
        })
    }

}