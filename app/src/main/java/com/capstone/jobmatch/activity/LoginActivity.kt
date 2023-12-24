package com.capstone.jobmatch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.jobmatch.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
//    private lateinit var loginFragment: LoginFragment
//    private lateinit var registerFragment: RegisterFragment
//    private lateinit var transaction: FragmentTransaction
//    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

//    private fun addFragment() {
//        loginFragment = LoginFragment()
//        fragmentManager = supportFragmentManager
//        transaction = fragmentManager.beginTransaction()
//        transaction.add(R.id.flFragmentContainer, loginFragment)
//        transaction.commit()
//    }
//
//    private fun replaceFragment() {
//        registerFragment = RegisterFragment()
//        fragmentManager = supportFragmentManager
//        transaction = fragmentManager.beginTransaction()
//        transaction.addToBackStack(null)
//        transaction.replace(R.id.flFragmentContainer, registerFragment)
//        transaction.commit()
//    }
}