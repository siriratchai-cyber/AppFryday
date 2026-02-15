package com.example.appfryday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MenuVerticalAdapter(
    private val list: List<MenuModel>
) : RecyclerView.Adapter<MenuVerticalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvMenuName)
        val price: TextView = view.findViewById(R.id.tvMenuPrice)
        val image: ImageView = view.findViewById(R.id.imgMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_vertical, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.name.text = item.Menu
        holder.price.text = "${item.Price} บาท"

        Glide.with(holder.itemView.context)
            .load(item.imgURL)
            .into(holder.image)
    }
}
