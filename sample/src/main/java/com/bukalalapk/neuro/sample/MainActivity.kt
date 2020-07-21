package com.bukalalapk.neuro.sample

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bukalapak.neuro.SimpleNeuro
import com.bukalapak.result.Response
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val router = SimpleNeuro<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerRoute()

        btnProceed.setOnClickListener {
            val response = router.proceed(etUrl.text.toString(), this)

            if (response.success){
                toast(this, response.body)
            }
        }
    }

    fun registerRoute() {
        val myRouter =  SimpleNeuro<String>()
        myRouter.setBase(Uri.parse("scales://"))
        myRouter.addPath("products"){
            return@addPath Response.success("{id:2, name:\"Яблоки\"}")
        }
        val response:Response<String> = myRouter.proceed("scales://products")
        //Log.e("TAG", response.body)

        router.setBase(Uri.parse("https://www.mywebsite.com"))

        // https://www.mywebsite.com/login
        router.addPath("/login") {
            return@addPath Response.success("Login")
        }

        // https://www.mywebsite.com/messages/1234
        router.addPath("/messages/<message_id>") {
            val messageId = it.variables.optString("message_id")
            return@addPath Response.success("Message with $messageId")
        }

        // https://www.mywebsite.com/promo?source=banner
        router.addPath("/promo") {
            val source = it.queries.optString("source")
            return@addPath Response.success("Promo with $source")
        }
    }

    fun toast(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
