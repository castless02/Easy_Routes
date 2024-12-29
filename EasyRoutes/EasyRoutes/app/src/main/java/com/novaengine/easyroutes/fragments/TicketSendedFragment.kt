package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.TicketsendedFragmentBinding

class TicketSendedFragment : Fragment(R.layout.ticketsended_fragment) {

    private lateinit var binding: TicketsendedFragmentBinding
    private lateinit var fm: FragmentManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            var Id = arguments?.getString("Id").toString()

            var bundle = Bundle()
            bundle.putString("Id",Id)

            val MF = ExploreFragment()
            MF.arguments = bundle


        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        binding = TicketsendedFragmentBinding.inflate(inflater)
        binding.fattoticket.setOnClickListener(){

            transaction.addToBackStack(null)
            transaction.replace(R.id.fragmentContainerView2, MF)
            transaction.commit()

        }


        return binding.root

    }
}