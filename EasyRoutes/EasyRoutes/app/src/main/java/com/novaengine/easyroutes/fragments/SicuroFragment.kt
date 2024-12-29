package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.SicuroFragmentBinding

class SicuroFragment : Fragment(R.layout.sicuro_fragment) {

    private lateinit var binding: SicuroFragmentBinding
    private lateinit var fm: FragmentManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var Id = arguments?.getString("Id").toString()
        var IdPrenotazione = arguments?.getString("IdPrenotazione").toString()
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        binding = SicuroFragmentBinding.inflate(inflater)

        var bundle = Bundle()
        bundle.putString("Id",Id)
        bundle.putString("IdPrenotazione",IdPrenotazione)

        val EF = EliminatoFragment()
        EF.arguments = bundle

        binding.si.setOnClickListener(){
            transaction.replace(R.id.fragmentContainerView2, EF)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.no.setOnClickListener(){
        }



        return binding.root

    }
}