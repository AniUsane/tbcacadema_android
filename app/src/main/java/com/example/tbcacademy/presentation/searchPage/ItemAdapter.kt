package com.example.tbcacademy.presentation.searchPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.databinding.ItemRecyclerBinding
import com.example.tbcacademy.domain.model.DomainItem

class OrdersDiffUtil: DiffUtil.ItemCallback<DomainItem>(){
    override fun areItemsTheSame(oldItem: DomainItem, newItem: DomainItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DomainItem, newItem: DomainItem): Boolean {
        return oldItem == newItem
    }

}

class ItemAdapter: ListAdapter<DomainItem, ItemAdapter.ItemViewHolder>(OrdersDiffUtil()) {
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
                fun onBind(item: DomainItem) {
                    binding.title.text = item.name
                    val bulletCount = minOf(item.depth, 4)
                    if (item.depth > 0) {
                        binding.bullet.visibility = View.VISIBLE
                        binding.bullet.text = "• ".repeat(bulletCount)
                    } else {
                        binding.bullet.visibility = View.INVISIBLE
                    }
                }
            }
}