package com.ks.aplikasidicodingevent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ks.aplikasidicodingevent.data.response.ListEventsItem
import com.ks.aplikasidicodingevent.databinding.ItemEventBinding

class EventAdapter(private val listener: OnItemClickListener) : ListAdapter<ListEventsItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class MyViewHolder(
        private val binding: ItemEventBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(event: ListEventsItem){
            itemView.setOnClickListener{
                listener.onItemClick(event)
            }
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.ivPicture)
            binding.tvTitle.text = event.name
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


