package com.jostin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jostin.conversorlogin.databinding.FragmentWelcomeBinding
import com.jostin.viewmodel.AuthViewModel
import com.jostin.viewmodel.AuthViewModelFactory
import com.jostin.data.AppDatabase
import com.jostin.data.UserRepository

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val repository = UserRepository(database.userDao())
        viewModel = ViewModelProvider(requireActivity(), AuthViewModelFactory(repository))[AuthViewModel::class.java]

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            binding.welcomeText.text = "Welcome, ${user?.name ?: "User"}!"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}