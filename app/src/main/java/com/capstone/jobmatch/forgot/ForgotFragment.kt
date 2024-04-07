package com.capstone.jobmatch.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.FragmentForgotBinding

class ForgotFragment : Fragment() {
    private var _binding: FragmentForgotBinding? = null
    private val viewModel by viewModels<ForgotPasswordViewModel>()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {

           val email = binding.etEmail.text.toString()
            if (email.isNotEmpty()){
                viewModel.sendResetPasswordEmail(email)
                Navigation.findNavController(it).navigate(R.id.action_forgotFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Link has been sent to your email", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Email is required", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}