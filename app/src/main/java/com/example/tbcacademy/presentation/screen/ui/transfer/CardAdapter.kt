package com.example.tbcacademy.presentation.screen.ui.transfer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.AccountCardBinding
import com.example.tbcacademy.presentation.model.CardUi

class CardDiffCallback : DiffUtil.ItemCallback<CardUi>() {
    override fun areItemsTheSame(oldItem: CardUi, newItem: CardUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CardUi, newItem: CardUi): Boolean =
        oldItem == newItem
}

class CardAdapter (
    private val onClick: (CardUi) -> Unit
) : ListAdapter<CardUi, CardAdapter.CardViewHolder>(CardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AccountCardBinding.inflate(inflater, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class CardViewHolder(private val binding: AccountCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: CardUi) = with(binding) {
            title.text = item.title
            cardNumber.text = item.maskedAccountNumber

            val logoRes = when (item.cardType.uppercase()) {
                "VISA" -> R.drawable.visa_image
                "MASTERCARD", "MASTER_CARD" -> R.drawable.mastercard_image
                else -> null
            }

            logoRes?.let { cardLogo.setImageResource(it) } ?: cardLogo.setImageDrawable(null)

            root.setOnClickListener {
                onClick(item)
            }
        }
    }

}