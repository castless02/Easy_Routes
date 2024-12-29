package com.novaengine.easyroutes.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.CouponFragment
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.AskFragmentBinding

class AskFragment : Fragment(R.layout.ask_fragment) {

    // Lateinit variables
    private lateinit var binding: AskFragmentBinding
    private lateinit var fm: FragmentManager
    private lateinit var sp: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Load the shared preferences
        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()

        // Fragment Manager
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Layout inflater
        binding = AskFragmentBinding.inflate(inflater)

        // Initialize a bundle
        var bundle = Bundle()
        bundle.putString("Id",Id)

        // FAQ Fragment
        val FF = FaqFragment()
        FF.arguments = bundle

        // Support Fragment
        val SF = SupportFragment()
        SF.arguments = bundle

        // Coupon Fragment
        val CF = CouponFragment()
        CF.arguments = bundle

        // FAQ Button
        binding.questionsButton.setOnClickListener(){
            transaction.addToBackStack("FaqFragment")
            transaction.replace(R.id.fragmentContainerView2, FF)
            transaction.commit()
        }

        // Support Button
        binding.assistenza.setOnClickListener(){
            transaction.addToBackStack("SupportFragment")
            transaction.replace(R.id.fragmentContainerView2, SF)
            transaction.commit()
        }

        // Coupons button
        binding.coupons.setOnClickListener(){
            transaction.addToBackStack("CouponFragment")
            transaction.replace(R.id.fragmentContainerView2, CF)
            transaction.commit()
        }

        return binding.root

    }
}