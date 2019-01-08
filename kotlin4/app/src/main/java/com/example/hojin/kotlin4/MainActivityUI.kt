package com.example.hojin.kotlin4

/**
 * Created by hojin on 7/1/2019.
 */

import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.w3c.dom.Text

class MainActivityUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>)= with(ui) {
        val ID_editText=1
        val ID_textview = 2
        val ID_button1 = 3
        val ID_button2 = 4
        var editText1: EditText

        relativeLayout{
            editText1 = editText{
                id = ID_editText
                hint = "enter city"


            }.lparams(width = matchParent){
                margin= dip(20)
                topMargin = dip (20)}
            var tv =textView{
                id= ID_textview
                textSize = 20.0f
                setText("i am a string")
            }.lparams(width = wrapContent){
                gravity = Gravity.CENTER_HORIZONTAL
                below(ID_editText)

                }
            button{
                setText("search")
                id = ID_button1
                width = wrapContent
                setOnClickListener{
                    Log.i("this is UI",editText1.text.toString())

                    var returnVar=ui.owner.QueryYahoo(ui,editText1.text.toString())
                    val inCelcius = returnVar.toDouble()-273.15
                    tv.text = String.format("The temperature at %s is %s  Kelvin,\n%s Celcius",
                            editText1.text.toString(),
                            returnVar,
                            inCelcius.toString())

                }
            }.lparams{
                below(ID_textview)

            }
            button{
                setText("just a button")
                id = ID_button2
                width = wrapContent
            }.lparams{
                rightOf(ID_button1)
                below(ID_textview)
            }

            }




    }

}