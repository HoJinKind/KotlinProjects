package com.example.hojin.kotlin1

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by hojin on 31/12/2018.
 */
class SecondActivityUI: AnkoComponent<SecondActivity> {
    override fun createView(ui: AnkoContext<SecondActivity>)= with(ui){
       frameLayout {
           val textfield = editText {
               hint = "text for toast"
           }.lparams(width = matchParent){
               margin = dip(12)
               topMargin= dip(30)
           }
           imageView(R.drawable.ic_android_black_24dp){
                setOnClickListener {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP&&this@imageView.imageTintList!=ColorStateList.valueOf(Color.parseColor("#4CAF50"))){

                        Log.i("buttonclick","test")
                        this@imageView.imageTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
                    }
                    else{
                        Log.i("buttonclick"," fail")

                        this@imageView.imageTintList = ColorStateList.valueOf(Color.parseColor("#000000"))
                    }
                }
           }.lparams(dip(50),dip(50)){
               gravity = Gravity.CENTER
           }
            linearLayout{
                button("change hint"){
                    setOnClickListener{
                        textfield.hint= "yes"
                        toast("worked")
                    }
                }
            }.lparams{
                gravity = Gravity.BOTTOM
                bottomMargin =dip(72)
            }

       }
    }
}