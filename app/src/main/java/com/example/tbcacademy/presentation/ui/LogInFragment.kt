package com.example.tbcacademy.presentation.ui

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.BaseFragment
import com.example.tbcacademy.R
import com.example.tbcacademy.data.remote.Resource
import com.example.tbcacademy.databinding.FragmentLogInBinding
import com.example.tbcacademy.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
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
            viewModel.login(email, password, rememberMe)
        }
    }

    private fun observer(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_logInFragment_to_profileFragment)
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), "Error: ${resource.errorMessage}", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            Toast.makeText(requireContext(), "Logging in...", Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

}