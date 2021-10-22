package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jorgegomezdeveloper.waycommerce.R
import com.jorgegomezdeveloper.waycommerce.model.Commerce

/**
 *   @author Jorge G.A.
 *   @since 22/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Adapter class for format the data of the commerces with the views.
 */
class WCListCommercesAdapter(private val commerces: List<Commerce>):
    RecyclerView.Adapter<WCListCommercesAdapter.ViewHolder>() {

// =================================================================================================
// Config
// =================================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_commerce, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val commerces = commerces[position]
        holder.nameCommerceTv.text = commerces.name
        holder.categoryCommerceTv.text = commerces.category
        holder.shortDescriptionCommerceTv.text = commerces.shortDescription
    }

    override fun getItemCount(): Int = commerces.size

// =================================================================================================
// View Holder
// =================================================================================================

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameCommerceTv: TextView =
            itemView.findViewById(R.id.nameCommerceTv)
        var categoryCommerceTv: TextView =
            itemView.findViewById(R.id.categoryCommerceTv)
        var shortDescriptionCommerceTv: TextView =
            itemView.findViewById(R.id.shortDescriptionCommerceTv)
    }
}