package com.example.hojin.kotlin3.helper

import android.graphics.*

/**
 * Created by hojin on 4/1/2019.
 */
class RectOverlay internal constructor(overlay: GraphicOverlay<RectOverlay>,
                                       private val bound: Rect?):GraphicOverlay.Graphic(overlay){

    private var recPaint :Paint
    init {
        recPaint = Paint()
        recPaint.color = RECT_COLOR
        recPaint.strokeWidth = STROKE_WIDTH
        recPaint.style = Paint.Style.STROKE

    }
    companion object {
        private val RECT_COLOR = Color.YELLOW
        private val STROKE_WIDTH = 8.0f

    }
    override fun draw(canvas: Canvas){
        val rect = RectF(bound)
        canvas.drawRect(rect,recPaint)
    }
}