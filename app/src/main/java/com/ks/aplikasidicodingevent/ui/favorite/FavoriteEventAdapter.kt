package com.ks.aplikasidicodingevent.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.ks.aplikasidicodingevent.databinding.ItemEventFavoriteBinding

class FavoriteEventAdapter(private val onItemClick: (FavoriteEvent) -> Unit) : Adapter<FavoriteEventAdapter.ViewHolder>(
) {

    private var favoriteEvent = listOf<FavoriteEvent>()

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteEvents(event: List<FavoriteEvent>) {
        favoriteEvent = event
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = favoriteEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = favoriteEvent[position]
        holder.bind(event)
        holder.itemView.setOnClickListener { onItemClick(event)}
    }

    class ViewHolder(private val binding: ItemEventFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEvent) {
            binding.tvItemName.text = event.name
            binding.tvItemDescription.text = event.summary
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imgItemPhoto)
        }
    }
}