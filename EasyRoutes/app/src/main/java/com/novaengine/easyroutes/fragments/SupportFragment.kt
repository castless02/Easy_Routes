package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.SupportFragmentBinding

class SupportFragment : Fragment(R.layout.support_fragment) {

    private lateinit var binding: SupportFragmentBinding
    private lateinit var fm: FragmentManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

          var Id = arguments?.getString("Id").toString()

            var bundle = Bundle()
            bundle.putString("Id",Id)

            val TSF = TicketSendedFragment()
            TSF.arguments = bundle


        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        binding = SupportFragmentBinding.inflate(inflater)

        binding.inviaticket.setOnClickListener(){
          if(binding.testoticket.text.isNotEmpty()){

              transaction.addToBackStack(null)
              transaction.replace(R.id.fragmentContainerView2, TSF)
              transaction.commit()

          }
        }

        return binding.root

    }
}