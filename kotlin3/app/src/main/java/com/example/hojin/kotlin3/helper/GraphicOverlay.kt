package com.example.hojin.kotlin3.helper
/*
* Copyright (C) The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.google.android.gms.vision.CameraSource
import java.util.HashSet
import java.util.Vector
/**
 * A view which renders a series of custom graphics to be overlayed on top of an associated preview
 * (i.e., the camera preview). The creator can add graphics objects, update the objects, and remove
 * them, triggering the appropriate drawing and invalidation within the view.<p>
 *
 * Supports scaling and mirroring of the graphics relative the camera's preview properties. The
 * idea is that detection items are expressed in terms of a preview size, but need to be scaled up
 * to the full view size, and also mirrored in the case of the front-facing camera.<p>
 *
 * Associated {@link Graphic} items should use the following methods to convert to view coordinates
 * for the graphics that are drawn:
 * <ol>
 * <li>{@link Graphic#scaleX(float)} and {@link Graphic#scaleY(float)} adjust the size of the
 * supplied value from the preview scale to the view scale.</li>
 * <li>{@link Graphic#translateX(float)} and {@link Graphic#translateY(float)} adjust the coordinate
 * from the preview's coordinate system to the view coordinate system.</li>
 * </ol>
 */
class GraphicOverlay<T : GraphicOverlay.Graphic>(context:Context, attrs:AttributeSet):View(context, attrs) {
    private var mLock = Any()
    private var mPreviewWidth:Int = 0
    /**
     * Returns the horizontal scale factor.
     */
    var widthScaleFactor = 1.0f
    private var mPreviewHeight:Int = 0
    /**
     * Returns the vertical scale factor.
     */
    var heightScaleFactor = 1.0f
    private var mFacing = CameraSource.CAMERA_FACING_BACK
    private var mGraphics = HashSet<T>()
    /**
     * Returns a copy (as a list) of the set of all active graphics.
     * @return list of all active graphics.
     */
    val graphics:List<T>
        get() {
            synchronized (mLock) {
                return Vector(mGraphics)
            }
        }
    /**
     * Base class for a custom graphics object to be rendered within the graphic overlay. Subclass
     * this and implement the {@link Graphic#draw(Canvas)} method to define the
     * graphics element. Add instances to the overlay using {@link GraphicOverlay#add(Graphic)}.
     */
    abstract class Graphic(overlay:GraphicOverlay<*>) {
        private val mOverlay:GraphicOverlay<*>
        init{
            mOverlay = overlay
        }
        /**
         * Draw the graphic on the supplied canvas. Drawing should use the following methods to
         * convert to view coordinates for the graphics that are drawn:
         * <ol>
         * <li>{@link Graphic#scaleX(float)} and {@link Graphic#scaleY(float)} adjust the size of
         * the supplied value from the preview scale to the view scale.</li>
         * <li>{@link Graphic#translateX(float)} and {@link Graphic#translateY(float)} adjust the
         * coordinate from the preview's coordinate system to the view coordinate system.</li>
         * </ol>
         *
         * @param canvas drawing canvas
         */
        abstract fun draw(canvas:Canvas)
        /**
         * Adjusts a horizontal value of the supplied value from the preview scale to the view
         * scale.
         */
        fun scaleX(horizontal:Float):Float {
            return horizontal * mOverlay.widthScaleFactor
        }
        /**
         * Adjusts a vertical value of the supplied value from the preview scale to the view scale.
         */
        fun scaleY(vertical:Float):Float {
            return vertical * mOverlay.heightScaleFactor
        }
        /**
         * Adjusts the x coordinate from the preview's coordinate system to the view coordinate
         * system.
         */
        fun translateX(x:Float):Float {
            if (mOverlay.mFacing == CameraSource.CAMERA_FACING_FRONT)
            {
                return mOverlay.getWidth() - scaleX(x)
            }
            else
            {
                return scaleX(x)
            }
        }
        /**
         * Adjusts the y coordinate from the preview's coordinate system to the view coordinate
         * system.
         */
        fun translateY(y:Float):Float {
            return scaleY(y)
        }
        fun postInvalidate() {
            mOverlay.postInvalidate()
        }
    }
    /**
     * Removes all graphics from the overlay.
     */
    fun clear() {
        synchronized (mLock) {
            mGraphics.clear()
        }
        postInvalidate()
    }
    /**
     * Adds a graphic to the overlay.
     */
    fun add(graphic:T) {
        synchronized (mLock) {
            mGraphics.add(graphic)
        }
        postInvalidate()
    }
    /**
     * Removes a graphic from the overlay.
     */
    fun remove(graphic:T) {
        synchronized (mLock) {
            mGraphics.remove(graphic)
        }
        postInvalidate()
    }
    /**
     * Sets the camera attributes for size and facing direction, which informs how to transform
     * image coordinates later.
     */
    fun setCameraInfo(previewWidth:Int, previewHeight:Int, facing:Int) {
        synchronized (mLock) {
            mPreviewWidth = previewWidth
            mPreviewHeight = previewHeight
            mFacing = facing
        }
        postInvalidate()
    }
    /**
     * Draws the overlay with its associated graphic objects.
     */
    protected override fun onDraw(canvas:Canvas) {
        super.onDraw(canvas)
        synchronized (mLock) {
            if ((mPreviewWidth != 0) && (mPreviewHeight != 0))
            {
                widthScaleFactor = canvas.getWidth() as Float / mPreviewWidth.toFloat()
                heightScaleFactor = canvas.getHeight() as Float / mPreviewHeight.toFloat()
            }
            for (graphic in mGraphics)
            {
                graphic.draw(canvas)
            }
        }
    }
}