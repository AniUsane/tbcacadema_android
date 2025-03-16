package com.example.tbcacademy.presentation.ui

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.base.BaseFragment
import com.example.tbcacademy.R
import com.example.tbcacademy.data.remote.Resource
import com.example.tbcacademy.databinding.FragmentLogInBinding
import com.example.tbcacademy.presentation.effect.LoginEffect
import com.example.tbcacademy.presentation.effect.RegisterEffect
import com.example.tbcacademy.presentation.event.LoginEvent
import com.example.tbcacademy.presentation.state.LoginState
import com.example.tbcacademy.presentation.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        listeners()
        observer()
    }

    private fun listeners(){
        binding.loginBtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val rememberMe = binding.rememberMe.isChecked
            viewModel.obtainEvent(LoginEvent.SubmitLogin(email, password, rememberMe))
        }
        binding.registerNow.setOnClickListener{
            viewModel.navigateToRegister()
        }
    }

    private fun observer(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect{ state ->
                when(state){
                    is LoginState.Loading -> showLoading()
                    is LoginState.Success -> {
                        hideLoading()
                        navigateToProfile()
                    }
                    is LoginState.Error -> {
                        hideLoading()
                        showSnackBar(state.message)
                    }
                    LoginState.Idle -> hideLoading()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effects.collect{ effect ->
                when(effect){
                    is LoginEffect.NavigateToProfile -> navigateToProfile()
                    is LoginEffect.ShowSnackBar -> showSnackBar(effect.message)
                    is LoginEffect.NavigateToRegister -> navigateToRegister()
                }
            }
        }
    }

    private fun navigateToRegister(){
        findNavController().navigate(R.id.action_logInFragment_to_registrationFragment)
    }

    private fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToProfile(){
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.logInFragment) {
            navController.navigate(R.id.action_logInFragment_to_profileFragment)
        }
    }

    private fun showLoading(){
        binding.loader.visibility = View.VISIBLE
        binding.loginBtn.isEnabled = false
    }

    private fun hideLoading(){
        binding.loader.visibility = View.GONE
        binding.loginBtn.isEnabled = true
    }

}