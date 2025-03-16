package com.example.tbcacademy.presentation.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.base.BaseFragment
import com.example.tbcacademy.databinding.FragmentProfileBinding
import com.example.tbcacademy.presentation.effect.ProfileEffect
import com.example.tbcacademy.presentation.event.ProfileEvent
import com.example.tbcacademy.presentation.state.ProfileState
import com.example.tbcacademy.presentation.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun start() {
        viewModel.obtainEvent(ProfileEvent.FetchUserEmail)
        observer()
        listeners()
    }

    private fun listeners(){
        binding.logoutBtn.setOnClickListener {
            viewModel.obtainEvent(ProfileEvent.Logout)
        }
    }

    private fun observer(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect{ state ->
                when(state) {
                    is ProfileState.Loading -> showLoading()
                    is ProfileState.Success -> {
                        hideLoading()
                        binding.emailText.text = state.email
                    }
                    is ProfileState.Error -> {
                        hideLoading()
                        showSnackBar(state.message)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effects.collect{ effect ->
                when(effect){
                    ProfileEffect.NavigateToLogin -> navigateToLogin()
                }
            }
        }
    }

    private fun navigateToLogin(){
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.profileFragment) {
            navController.navigate(R.id.action_profileFragment_to_logInFragment)
        }
    }

    private fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(){
        binding.loader.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loader.visibility = View.GONE
    }

}