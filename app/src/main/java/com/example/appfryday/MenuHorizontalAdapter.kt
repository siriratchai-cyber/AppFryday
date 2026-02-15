package com.example.appfryday

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot

class MenuHorizontalAdapter(
    private val menuList: List<DocumentSnapshot>
) : RecyclerView.Adapter<MenuHorizontalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvMenuName)
        val price = view.findViewById<TextView>(R.id.tvMenuPrice)
        val image = view.findViewById<ImageView>(R.id.imgMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_horizontal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = menuList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val doc = menuList[position]

        holder.name.text = doc.getString("Menu")
        holder.price.text = "${doc.getLong("Price")} บาท"

        Glide.with(holder.itemView.context)
            .load(doc.getString("imgURL"))
            .into(holder.image)
    }
}
