package com.capstone.jobmatch.core.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.capstone.jobmatch.core.ui.main.MainActivity
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }


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
        session()
        binding.btnRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tvForgotPassword.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_forgotFragment)
        }

        binding.btnLogin.setOnClickListener {
            loginUserAccount()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun session(){
        val user = mAuth.currentUser
        if (user != null){
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }


    private fun loginUserAccount() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), "Please enter email...", Toast.LENGTH_LONG).show()
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(), "Please enter password!", Toast.LENGTH_LONG).show()
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                }
                else {
                    Toast.makeText(requireContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show()
                }
            }
        }


    }


}
