package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.DatimodificatiFragmentBinding

class DatiModificatiFragment : Fragment(R.layout.datimodificati_fragment) {

    private lateinit var binding: DatimodificatiFragmentBinding
    private lateinit var fm: FragmentManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var Id = arguments?.getString("Id").toString()

        var bundle = Bundle()
        bundle.putString("Id",Id)

        val MPF = MainProfileFragment()
        MPF.arguments = bundle


        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        binding = DatimodificatiFragmentBinding.inflate(inflater)
        binding.ok.setOnClickListener(){
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        return binding.root

    }
}