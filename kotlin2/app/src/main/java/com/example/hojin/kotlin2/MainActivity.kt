package com.example.hojin.kotlin2

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        estimatePi().execute(10)
    }
    class estimatePi : AsyncTask<Int?, Unit, String>() {

        override protected fun doInBackground(vararg params: Int?): String {
            var a :Int? = params!![0]
            //is there an easier way to do this?
            for (i in 0..a!!) {
                a += i!!
                Log.i("hi",a.toString())
            }

            return "hi"
        }


        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Log.i("beye",result )
        }
    }
}

private operator fun Unit.rangeTo(unit: Unit) {}
