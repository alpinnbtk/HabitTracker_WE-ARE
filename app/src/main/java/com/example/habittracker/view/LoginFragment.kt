package com.example.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentLoginBinding
import com.example.habittracker.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.loginResultLD.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.action_dashboard)
            } else {
                Toast.makeText(requireContext(), "Login gagal!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            viewModel.login(username, password)
        }
    }
}