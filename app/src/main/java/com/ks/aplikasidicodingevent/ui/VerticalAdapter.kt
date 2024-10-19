package com.ks.aplikasidicodingevent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ks.aplikasidicodingevent.data.response.ListEventsItem
import com.ks.aplikasidicodingevent.databinding.ItemEventHomeVerticalBinding

class VerticalAdapter(private val listener: OnItemClickListener) : ListAdapter<ListEventsItem, VerticalAdapter.EventVerticalViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventVerticalViewHolder {
        val bindingVertical = ItemEventHomeVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventVerticalViewHolder(bindingVertical, listener)
    }

    override fun onBindViewHolder(holder: EventVerticalViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventVerticalViewHolder(
        private val binding: ItemEventHomeVerticalBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.root.setOnClickListener { listener.onItemClick(event) }
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = event.name
            binding.tvItemDescription.text = event.summary
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(event: ListEventsItem)
    }
}