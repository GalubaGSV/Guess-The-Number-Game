package com.example.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.guessthenumber.model.RandomOrgRequest
import com.example.guessthenumber.model.RequestParam
import com.example.guessthenumber.service.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.testTextView)
        val button = findViewById<Button>(R.id.button)


        button.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                //hardcode start
                val requestParam = RequestParam(
                    "0bab98af-5798-4c1b-a59d-304507830c39",
                    1,
                    1,
                    100,
                    true
                )

                val request = RandomOrgRequest(
                    "2.0",
                    "generateIntegers",
                    requestParam,
                    1
                )


                //hardcode end

                val randomNumberResponse = RetrofitInstance.randomIntApi.getRandomNumber(request)

                runOnUiThread{
                    tv.text = randomNumberResponse.result.random.data[0].toString()
                }
            }
        }



    }
}