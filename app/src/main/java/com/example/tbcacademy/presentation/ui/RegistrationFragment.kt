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
import com.example.tbcacademy.databinding.FragmentRegistrationBinding
import com.example.tbcacademy.presentation.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
            viewModel.register(email, password, repeatedPassword)
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collectLatest { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registrationFragment_to_logInFragment)
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }






}