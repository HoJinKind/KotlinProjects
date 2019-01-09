package com.example.hojin.kotlin6

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hojin.kotlin6.R.id.left
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        left_button.setOnClickListener {
            rangebar1.tickStart = 2.0f//testing out. seems to hv failed
            Log.i("show status",seekbar1.progress.toString())
            seekbar1.progress = seekbar1.progress-1//able to achieve effect

        }
        right_button.setOnClickListener{
            seekbar1.progress = seekbar1.progress+1//able to achieve effect
            Log.i("show status",seekbar1.progress.toString())

        }

    }
}
