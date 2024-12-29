package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.RecuperatoFragmentBinding

class RecuperatoFragment : Fragment(R.layout.recover_password_fragment) {

    private lateinit var binding: RecuperatoFragmentBinding
    private lateinit var fm: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        binding = RecuperatoFragmentBinding.inflate(inflater)
        binding.indietro.setOnClickListener(){
            transaction.replace(R.id.fragmentContainerView, LoginFragment())
            transaction.commit()
        }
        return binding.root

    }
}