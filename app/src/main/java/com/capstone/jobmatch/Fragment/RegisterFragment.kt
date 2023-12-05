package com.capstone.jobmatch.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }





}
