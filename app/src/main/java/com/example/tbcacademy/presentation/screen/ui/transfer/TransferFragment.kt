package com.example.tbcacademy.presentation.screen.ui.transfer

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.example.tbcacademy.BaseFragment
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.FragmentTransferBinding
import com.example.tbcacademy.presentation.model.CardUi
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransferFragment : BaseFragment<FragmentTransferBinding>(FragmentTransferBinding::inflate) {

    private val viewModel: TransferViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    override fun start() {
        listeners()
        observeState()
        observeEffects()

        viewModel.obtainEvent(TransferEvent.LoadCards)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener("to_account_result", this) { _, bundle ->
            val card = bundle.getSerializable("card", CardUi::class.java)
            card?.let {
                viewModel.obtainEvent(TransferEvent.OnToCardSelected(it))
            }
        }
    }

    private fun listeners() {
        binding.fromCard.setOnClickListener {
            viewModel.obtainEvent(TransferEvent.FromCardClicked)
        }

        binding.toCard.setOnClickListener {
            viewModel.obtainEvent(TransferEvent.ToCardClicked)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                binding.loader.isVisible = state.isLoading

                state.error?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }

                state.selectedFromCard?.let { card ->
                    showFromAccount(card)
                }

                state.selectedToCard?.let { card ->
                    showToAccount(card)
                }
            }
        }
    }


    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is TransferEffect.ClickFromBottomSheet -> showFromAccountBottomSheet()
                    is TransferEffect.ClickToBottomSheet -> showToAccountBottomSheet()
                    is TransferEffect.ShowError -> {
                        Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_SHORT).show()
                    }

                    is TransferEffect.SubmitToCard -> {
                        viewModel.obtainEvent(TransferEvent.OnToCardSelected(effect.account))
                    }
                }
            }
        }
    }

    private fun showFromAccountBottomSheet() {
        val sheet = FromAccountBottomSheet()
        sheet.show(childFragmentManager, "from_sheet")
    }

    private fun showToAccountBottomSheet(){
        val sheet = ToAccountBottomSheet()
        sheet.show(childFragmentManager, "to_sheet")
    }

    private fun showFromAccount(card: CardUi) = with(binding) {
        cashLabel.text = card.title
        fromCardNumbers.text = card.maskedAccountNumber
        amountOfMoney.text = card.balanceFormatted

        val logoRes = when (card.cardType.uppercase()) {
            "VISA" -> R.drawable.visa_image
            "MASTER_CARD" -> R.drawable.mastercard_image
            else -> 0
        }

        if (logoRes != 0) {
            cardType.setImageResource(logoRes)
        } else {
            cardType.setImageDrawable(null)
        }

        fromCard.isVisible = true
    }

    private fun showToAccount(card: CardUi) = with(binding) {
        toCashLabel.text = card.title
        toCardNumbers.text = card.maskedAccountNumber
        toAmountOfMoney.text = card.balanceFormatted

        val logoRes = when (card.cardType.uppercase()) {
            "VISA" -> R.drawable.visa_image
            "MASTERCARD", "MASTER_CARD" -> R.drawable.mastercard_image
            else -> 0
        }

        toCardType.setImageResourceOrNull(logoRes)
        toCard.isVisible = true
    }

    private fun AppCompatImageView.setImageResourceOrNull(resId: Int) {
        if (resId != 0) {
            setImageResource(resId)
        } else {
            setImageDrawable(null)
        }
    }
}