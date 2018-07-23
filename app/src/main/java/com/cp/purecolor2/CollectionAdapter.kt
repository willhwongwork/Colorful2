package com.cp.purecolor2

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cp.purecolor2.data.Colors
import com.cp.purecolor2.view.ColorView

class CollectionAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var colors: List<Colors>? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return CollectionViewHolder.create(parent!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as CollectionViewHolder
        val color = colors?.get(position)
        Log.d("CollectionAdapter", "hex: " + color?.hex)

        if(color?.hex!!.length > 4 ) {
            holder.colorView.setColor(Color.parseColor(color?.hex))
        } else {
            val rgb = color!!.rgb
            val red = rgb.get(0).toInt()
            val green = rgb.get(1).toInt()
            val blue = rgb.get(2).toInt()
            holder.colorView.setColor(Color.rgb(red, green, blue))
        }
        holder.colorName.text = color!!.name

        holder.itemView.setOnClickListener{view:View ->
            val f: ColorViewDialogFragment = ColorViewDialogFragment.newInstance(color)
            val act = view.context as AppCompatActivity
            f.show(act.supportFragmentManager, "set color")
        }
    }
    
    override fun getItemCount(): Int {
        if(colors != null) {
            return colors!!.size
        } else{
            return 0
        }
    }

    fun setColors(colors: List<Colors>) {
        this.colors = colors
        notifyDataSetChanged()
    }

    class CollectionViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val colorView: ColorView = view.findViewById(R.id.color_view)
        val colorName : TextView = view.findViewById(R.id.color_name)

        companion object {
            fun create(parent: ViewGroup) : CollectionViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_color_view, parent, false)
                return CollectionViewHolder(view)
            }
        }

    }

}