package com.example.hojin.kotlin3

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.hojin.kotlin3.helper.GraphicOverlay
import com.example.hojin.kotlin3.helper.RectOverlay
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.wonderkiln.camerakit.*
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var waitingDialog: android.app.AlertDialog
    override fun onResume() {
        super.onResume()
        camera_view.start()
    }

    override fun onPause() {
        super.onPause()
        camera_view.stop()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation =(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        waitingDialog = SpotsDialog.Builder().setContext(this).build()
        button_detect.setOnClickListener{

            camera_view.start()
            camera_view.captureImage()
        graphic_overlay.clear()}
        camera_view.addCameraKitListener( object : CameraKitEventListener{
            override fun onImage(p0: CameraKitImage?) {
                waitingDialog.show()
                var bitmap = p0!!.bitmap
                bitmap = Bitmap.createScaledBitmap(bitmap,camera_view.width,camera_view.height,false)
                camera_view.stop()
                runDetector(bitmap)
            }

            override fun onVideo(p0: CameraKitVideo?) {
            }

            override fun onEvent(p0: CameraKitEvent?) {
            }

            override fun onError(p0: CameraKitError?) {
            }




    })
}

    private fun runDetector(bitmap: Bitmap?) {
        val image = FirebaseVisionImage.fromBitmap(bitmap!!)
        val options= FirebaseVisionBarcodeDetectorOptions.Builder().
        setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_QR_CODE,
                FirebaseVisionBarcode.FORMAT_CODABAR
        )
                .build()
        val barcodeDetector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
        barcodeDetector.detectInImage(image)
                .addOnSuccessListener { result ->processResult(result)
                }
                .addOnFailureListener{e->Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()}
        
}

    private fun processResult(result: List<FirebaseVisionBarcode>) {
    for (item in result){
        var bounds = item.boundingBox
        var raw_value = item.rawValue
        var value_type = item.valueType
        val rect = RectOverlay(graphic_overlay as GraphicOverlay<RectOverlay>,bounds)
        (graphic_overlay as GraphicOverlay<RectOverlay>).add(rect)
        when(value_type){
            FirebaseVisionBarcode.TYPE_TEXT->{
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage(raw_value)
                alertDialog.setPositiveButton("ok",{dialog,which ->dialog.dismiss()})
                val dialog = alertDialog.create()
                dialog.show()
            }
            FirebaseVisionBarcode.TYPE_URL->{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(raw_value))
                startActivity(intent)
            }
            FirebaseVisionBarcode.TYPE_CONTACT_INFO->{
                val name = item.contactInfo!!.name!!.formattedName
                val address = item.contactInfo!!.addresses[0].addressLines[0]
                val email = item. contactInfo!!.emails[0].address
                val info = StringBuilder("Name: ").append(name).append("\n")
                        .append("Address: ").append(address).append("\n")
                        .append("Email: ").append(email).toString()
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage(info)
                alertDialog.setPositiveButton("ok",{dialog,which ->dialog.dismiss()})
                val dialog = alertDialog.create()

            }
        }
    }
            waitingDialog.dismiss()
    }
}

