package com.ks.aplikasidicodingevent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ks.aplikasidicodingevent.data.response.ListEventsItem
import com.ks.aplikasidicodingevent.databinding.ItemEventHomeHorizontalBinding

class HorizontalAdapter(private val listener: OnItemClickListener) : ListAdapter<ListEventsItem, HorizontalAdapter.EventHorizontalViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHorizontalViewHolder {
        val bindingHorizontal = ItemEventHomeHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHorizontalViewHolder(bindingHorizontal, listener)
    }

    override fun onBindViewHolder(holder: EventHorizontalViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventHorizontalViewHolder(
        private val binding: ItemEventHomeHorizontalBinding,
        private val listener: OnItemClickListener
    ) :  ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.root.setOnClickListener { listener.onItemClick(event) }
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.ivPictureHome)
            binding.tvTitleHome.text = event.name
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