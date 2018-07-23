package com.cp.purecolor2

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_picker.*
import java.io.IOException








class ColorPickerFragment: Fragment() {
    companion object {
        val ARG_PICKER = "ARG_PICKER"

        fun newInstance(tab: Int):ColorPickerFragment {
            val args = Bundle()
            args.putInt(ARG_PICKER, tab)
            val fragment = ColorPickerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_picker, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
        set_wp_button.setOnClickListener {view ->
            val color = colorPickerView.color
            Log.d("ColorPickerFragment", "color fixed $color")
            setWPService(color)
            //setWallpaper(color)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun setWPService(color: Int) {
        val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(context, ColorWallPaperService::class.java))

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putInt(getString(R.string.pref_wallpaper_color), color).commit()
        startActivity(intent)
    }

    fun setWallpaper(color: Int) {
        val wm = WallpaperManager.getInstance(context)
        val drawable = ColorDrawable(color)
        val bitmap = drawableToBitmap(drawable)
        Log.i(javaClass.name, "bitmap = $bitmap")
        try {
            wm.setBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Toast.makeText(context, "Wallpaper changed",
                Toast.LENGTH_LONG).show()
    }

    private fun drawableToBitmap(drawable: ColorDrawable): Bitmap {
        var bitmap: Bitmap? = null

        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable as BitmapDrawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Log.d("ColorPickerFragment", "intrinsicWidth " + drawable.intrinsicWidth)
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }


}