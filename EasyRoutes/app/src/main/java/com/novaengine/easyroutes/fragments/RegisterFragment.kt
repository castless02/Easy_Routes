package com.novaengine.easyroutes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.RegisterFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService
import com.novaengine.easyroutes.serversupport.dataclasses.RegisterData

class RegisterFragment : Fragment(R.layout.register_fragment) {

    private lateinit var binding: RegisterFragmentBinding
    private lateinit var fm: FragmentManager

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Fragment manager
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Layout inflater
        binding = RegisterFragmentBinding.inflate(inflater)

        // Go back button
        binding.goBackButton.setOnClickListener(){
            transaction.replace(R.id.fragmentContainerView, LoginFragment())
            transaction.commit()
        }

        // Register button
        binding.registerButton.setOnClickListener {
            val nome = binding.nameInput.text
            val cognome = binding.surnameInput.text
            val email = binding.emailInput.text
            val password = binding.passwordInput.text

            binding.icon1.visibility = if (nome.isNotEmpty()) View.INVISIBLE else View.VISIBLE
            binding.icon2.visibility = if (cognome.isNotEmpty()) View.INVISIBLE else View.VISIBLE
            binding.icon3.visibility = if (Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()) View.INVISIBLE else View.VISIBLE
            binding.icon4.visibility = if (password.isNotEmpty()) View.INVISIBLE else View.VISIBLE
            binding.errorMessage.visibility = if (nome.isEmpty() || cognome.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches() || password.isEmpty()) View.VISIBLE else View.INVISIBLE

            if (binding.errorMessage.isVisible) {
                binding.errorMessage.text = resources.getString(R.string.register_error_empty_fields)
            }

            if (nome.isNotEmpty() && cognome.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches() && password.isNotEmpty()) {
                val registerData = RegisterData(nome.toString(), cognome.toString(), email.toString(), password.toString())
                DBQueriesService().register(registerData) { status ->
                    if (status) {
                        transaction.replace(R.id.fragmentContainerView, RegistredFragment())
                        transaction.commit()
                    } else {
                        binding.errorMessage.visibility = View.VISIBLE
                        binding.errorMessage.text = resources.getString(R.string.register_error_general_issue)
                    }
                }
            }
        }

        return binding.root

    }
}