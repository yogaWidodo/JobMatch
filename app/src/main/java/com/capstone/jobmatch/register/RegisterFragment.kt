package com.capstone.jobmatch.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.FragmentRegisterBinding
import org.koin.android.ext.android.inject


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    val viewModel by inject<RegisterViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            viewModel.registerNewUser(it,binding, requireContext())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
