package com.cp.purecolor2

import android.app.Dialog
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cp.purecolor2.data.Colors
import com.cp.purecolor2.data.DataRepository
import com.cp.purecolor2.view.ColorView
import java.util.*



class ColorViewDialogFragment: DialogFragment() {

    private var hex: String = ""
    private var name: String = ""
    private lateinit var rgb: ArrayList<String>
    private var color: Int = 0
    private lateinit var repository:DataRepository

    companion object {
        fun newInstance(color: Colors): ColorViewDialogFragment {
            val f: ColorViewDialogFragment = ColorViewDialogFragment()

            val args = Bundle()
            args.putString("hex", color.hex)
            args.putString("name", color.name)
            args.putStringArrayList("rgb", color.rgb as ArrayList<String>?)
            f.arguments = args

            return f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hex = arguments.getString("hex")
        name = arguments.getString("name")
        rgb = arguments.getStringArrayList("rgb")
        repository = DataRepository(context, Injection.provideUserDataSource(context), Injection.provideExecutor())
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(activity)
        // Get the layout inflater
        val inflater = activity.layoutInflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view_dialog: View = inflater.inflate(R.layout.fragment_dialog_color, null)
        val dialog_colorView: ColorView = view_dialog.findViewById(R.id.dialog_colorView)
        val dialog_colorName: TextView = view_dialog.findViewById(R.id.dialog_colorName)

        if(hex.length > 4) {
            color = Color.parseColor(hex)
            dialog_colorView.setColor(Color.parseColor(hex))
        } else {
            val red = rgb.get(0).toInt()
            val green = rgb.get(1).toInt()
            val blue = rgb.get(2).toInt()
            color = Color.rgb(red, green, blue)
            dialog_colorView.setColor(Color.rgb(red, green, blue))
        }
        dialog_colorName.setText(name)

        val buider = builder.setView(view_dialog)
                .setPositiveButton("set wallpaper", { dialogInterface: DialogInterface, id: Int ->
                    setWPService(color)

                })
                .setNegativeButton("cancel", { dialogInterface: DialogInterface, id: Int ->

                })

        if(!isFaves()) {
            buider.setNeutralButton("add favorite", { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(context, "setting as favorite", Toast.LENGTH_SHORT).show()
                addFavorite()
            })
        } else {
            buider.setNeutralButton("delete favorite", { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(context, "deleting favorite", Toast.LENGTH_SHORT).show()
                deleteFaves()
            })
        }

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (view != null) {
            val parent = view!!.parent as ViewGroup
            parent?.removeAllViews()
        }
    }

    private fun setWPService(color: Int) {
        val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(context, ColorWallPaperService::class.java))

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putInt(getString(R.string.pref_wallpaper_color), color).commit()
        startActivity(intent)
    }

    private fun addFavorite() {
        val colors = Colors(name, hex, rgb)
        repository.saveFavs(colors)
    }

    private fun isFaves():Boolean {
        val colors = Colors(name, hex, rgb)
        val contained = repository.isFaves(colors)
        Log.d("ColorViewDialogFragment", "contain " + contained)
        return contained
    }

    private fun deleteFaves() {
        val colors = Colors(name, hex, rgb)
        repository.deleteFaves(colors)
    }
}
