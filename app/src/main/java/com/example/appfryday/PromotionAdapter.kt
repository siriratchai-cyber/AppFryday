package com.example.appfryday

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot

class PromotionAdapter(
    private val promoList: List<DocumentSnapshot>
) : RecyclerView.Adapter<PromotionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imgPromotion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_promotion, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = promoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val doc = promoList[position]

        Glide.with(holder.itemView.context)
            .load(doc.getString("imgURL"))
            .into(holder.image)
    }
}
