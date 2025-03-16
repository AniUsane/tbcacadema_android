package com.example.tbcacademy.presentation.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.base.BaseFragment
import com.example.tbcacademy.databinding.FragmentRegistrationBinding
import com.example.tbcacademy.presentation.effect.RegisterEffect
import com.example.tbcacademy.presentation.event.RegisterEvent
import com.example.tbcacademy.presentation.state.RegisterState
import com.example.tbcacademy.presentation.viewmodel.RegistrationViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {
    private val viewModel: RegistrationViewModel by viewModels()

    override fun start() {
        listeners()
        observer()
    }

    private fun listeners(){
        binding.registerBtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repeatedPassword = binding.repeatPassword.text.toString()
            viewModel.obtainEvent(RegisterEvent.SubmitRegistration(email, password, repeatedPassword))
        }

        binding.alreadyUser.setOnClickListener{
            viewModel.navigateToLoginPage()
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect{ state ->
                when(state){
                    is RegisterState.Loading -> showLoading()
                    is RegisterState.Success -> navigateToLogin()
                    is RegisterState.Error -> showSnackBar(state.message)
                    else -> hideLoading()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effects.collect{ effect ->
                when(effect) {
                    is RegisterEffect.NavigateToLogin -> navigateToLogin()
                    is RegisterEffect.ShowSnackBar -> showSnackBar(effect.message)
                    is RegisterEffect.NavigateToLoginPage -> navigateToLoginPage()
                }
            }
        }
    }

    private fun navigateToLoginPage(){
        findNavController().navigate(R.id.action_registrationFragment_to_logInFragment)
    }

    private fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToLogin(){
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.registrationFragment) {
            navController.navigate(R.id.action_registrationFragment_to_logInFragment)
        }
    }

    private fun showLoading(){
        binding.loader.visibility = View.VISIBLE
        binding.registerBtn.isEnabled = false
    }

    private fun hideLoading(){
        binding.loader.visibility = View.GONE
        binding.registerBtn.isEnabled = true
    }
}