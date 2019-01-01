package com.example.hojin.kotlin1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.databinding.DataBindingUtil
import android.databinding.BindingAdapter
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            //Your code here
            Log.i("buttonclick","q")
            val intent = Intent(this,SecondActivity::class.java)
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent)
        }

        fun clickTest(){
        Log.i("buttonclick"," test")
        val intent = Intent(this,SecondActivity::class.java)
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent)
    }

}
}
