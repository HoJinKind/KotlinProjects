package com.example.hojin.kotlin5

import android.app.Activity;
import android.app.Dialog;
import android.system.Os.bind
import android.util.Log
import android.view.Window;
import android.widget.ImageView;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.hojin.kotlin5.R.id.custom_loading_imageView
import com.example.hojin.kotlin5.R.layout.gif_image_view
import kotlinx.android.synthetic.main.gif_image_view.*

/**
 * Created by hojin on 9/1/2019.
 */
class ViewDialog(activity:Activity) {
    internal var activity: Activity
    internal lateinit var dialog: Dialog
    internal lateinit var imgView: ImageView//learning lesson, image view is of dialog, not activity.


    init{
        this.activity = activity
        Log.i("create VD","")
    }
    fun showDialog() {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)//change tis if triggered  to end after async task
        dialog.setContentView(R.layout.gif_image_view)
        imgView = dialog.custom_loading_imageView//take from dialog, since dialog XML is the gif one

        var imageViewTarget = GlideDrawableImageViewTarget(imgView)
        Glide.with(activity)
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .centerCrop()
                .crossFade()
                .into(imageViewTarget)
        dialog.show()
    }
    fun hideDialog() {
        dialog.dismiss()
    }
}