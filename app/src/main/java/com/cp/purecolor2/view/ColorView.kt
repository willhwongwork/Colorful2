package com.cp.purecolor2.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.View


class ColorView(context: Context?, attributeSet: AttributeSet) : View(context, attributeSet) {

    lateinit var paint: Paint
    var radius: Float = 1.0F
    var xW: Float = 0.0F
    var yH: Float = 0.0F
    var mShowColor: Boolean = false

    override protected fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(mShowColor) {
            Log.d("ColorView", "draw circle " + xW + " " + yH + " paint color: " + paint.color)
            canvas.drawCircle(xW/2, yH/2, radius, paint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        xW = width.toFloat()
        yH = height.toFloat()
        radius = xW / 2
    }

    public fun setColor(color: Int) {
        mShowColor = true
        initPaint(color)
        invalidate()
        requestLayout()
    }

    public fun isShowColor(): Boolean {
        return mShowColor
    }

    private fun initPaint(color: Int) {
        paint = Paint(ANTI_ALIAS_FLAG)
        paint.color = color
        paint.style = Paint.Style.FILL_AND_STROKE
    }
}