package com.example.tbcacademy.presentation.searchPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.data.model.Item
import com.example.tbcacademy.databinding.ItemRecyclerBinding

class OrdersDiffUtil: DiffUtil.ItemCallback<Item>(){
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}

class ItemAdapter: ListAdapter<Item, ItemAdapter.ItemViewHolder>(OrdersDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        return ItemViewHolder(
            ItemRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    inner class ItemViewHolder(private val binding: ItemRecyclerBinding):
            RecyclerView.ViewHolder(binding.root){
                fun onBind(item: Item) {
                    binding.title.text = item.name


                }
            }
}