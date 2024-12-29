package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.RecoverPasswordFragmentBinding

class RecoverPasswordFragment : Fragment(R.layout.recover_password_fragment) {

    private lateinit var binding: RecoverPasswordFragmentBinding
    private lateinit var fm: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Fragment maanger
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Layout inflater
        binding = RecoverPasswordFragmentBinding.inflate(inflater)

        // Go back button
        binding.goBackButton.setOnClickListener(){
            transaction.replace(R.id.fragmentContainerView, LoginFragment())
            transaction.commit()
        }

        // Confirm recover button
        binding.confirmRecoverButton.setOnClickListener(){

            if(binding.recoverEmailInput.text.isNotEmpty()){
                transaction.replace(R.id.fragmentContainerView, RecuperatoFragment())
                transaction.commit()
                binding.icon5.visibility = View.INVISIBLE
                binding.errorMessage.visibility = View.INVISIBLE

            }else{
                binding.icon5.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.VISIBLE
            }


        }

        return binding.root

    }
}