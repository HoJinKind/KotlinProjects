package com.example.hojin.kotlin1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.databinding.DataBindingUtil
import android.databinding.BindingAdapter
import org.jetbrains.anko.setContentView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SecondActivityUI().setContentView(this)
    }
}
