package com.jostin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jostin.conversorlogin.databinding.FragmentRegisterBinding
import com.jostin.data.AppDatabase
import com.jostin.data.UserRepository
import com.jostin.viewmodel.AuthViewModel
import com.jostin.viewmodel.AuthViewModelFactory

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el DAO y el repository
        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        val userRepository = UserRepository(userDao)

        // Usa la misma factory que en LoginFragment
        val factory = AuthViewModelFactory(userRepository)
        viewModel = ViewModelProvider(requireActivity(), factory)[AuthViewModel::class.java]

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.register(email, password, name) { success ->
                    if (success) {
                        Snackbar.make(binding.root, "Registration successful. Please login.", Snackbar.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    } else {
                        Snackbar.make(binding.root, "Registration failed. Please try again.", Snackbar.LENGTH_LONG).show()
                    }
                }
            } else {
                Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}