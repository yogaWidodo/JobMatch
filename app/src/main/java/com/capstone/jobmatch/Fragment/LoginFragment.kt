package com.capstone.jobmatch.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.capstone.jobmatch.activity.MainActivity
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener{
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }




}
