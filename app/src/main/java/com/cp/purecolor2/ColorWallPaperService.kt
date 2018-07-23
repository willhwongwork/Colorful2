package com.cp.purecolor2

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.preference.PreferenceManager
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder



class ColorWallPaperService : WallpaperService() {

    override fun onCreateEngine(): WallpaperService.Engine {
        // TODO Auto-generated method stub
        return ColorWallpaperEngine()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.

        return START_STICKY
    }

    private inner class ColorWallpaperEngine: WallpaperService.Engine() {
        private val handler = Handler()
        private val drawRunner = Runnable { draw() }
        private var color: Int = 10
        private val paint = Paint()
        private var width: Int = 0
        private var height: Int = 0
        private var visible = true

        init {
            //paint.setAntiAlias(true);
            //paint.setColor(Colors.CYAN);
            //paint.setStyle(Paint.Style.FILL);
            getColorPref()
            handler.post(drawRunner)
        }

        fun getColorPref(){
            val prefs = PreferenceManager
                    .getDefaultSharedPreferences(this@ColorWallPaperService)

            color = prefs.getInt(getString(R.string.pref_wallpaper_color), 0)
            Log.d("ColorWallPaperService", "engine color " + color)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) {
                handler.post(drawRunner)
            } else {
                handler.removeCallbacks(drawRunner)
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            this.visible = false
            handler.removeCallbacks(drawRunner)
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            this.width = width
            this.height = height
            super.onSurfaceChanged(holder, format, width, height)
        }

        private fun draw() {
            val holder = surfaceHolder
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                if (canvas != null) {
                    getColorPref()
                    Log.d("ColorWallPaperService", "drawing test " + color)
                    canvas.drawColor(color)

                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas)
                }
            }
            handler.removeCallbacks(drawRunner)
            if (visible) {
                handler.postDelayed(drawRunner, 5000)
            }

        }

    }

}
