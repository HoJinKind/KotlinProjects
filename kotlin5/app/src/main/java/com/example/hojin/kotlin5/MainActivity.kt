package com.example.hojin.kotlin5

import android.app.Activity
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log
import android.view.View;
import android.widget.Button
import com.example.hojin.kotlin5.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity:AppCompatActivity() {
    internal lateinit var viewDialog:ViewDialog
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        try{
        Log.i("test in main1","")//nt executed
            viewDialog = ViewDialog(this)
        }catch (ex:Exception){
            Log.i("first pt","Main")
        }
        start.setOnClickListener{
            //..show gif and hide after 5 seconds
            viewDialog.showDialog()
            val handler = Handler()
            handler.postDelayed(object:Runnable {
                public override fun run() {
                    viewDialog.hideDialog()
                }
            }, 7000)
        }
        start1.setOnClickListener{
            if(start1.text=="success"){
                start1.text="fail"
            }else{start1.text="success"}

            Log.i("test in main1","")//nt executed
        }
    }
    fun showCustomLoadingDialog() {

    }
}
