package com.novaengine.easyroutes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.databinding.CouponmessoFragmentBinding
import com.novaengine.easyroutes.fragments.ExploreFragment

class CouponMessoFragment : Fragment(R.layout.coupon_fragment) {

    private lateinit var binding: CouponmessoFragmentBinding
    private lateinit var fm: FragmentManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var Id = arguments?.getString("Id").toString()
        var Credito = arguments?.getString("Credito").toString()

        var bundle = Bundle()
        bundle.putString("Id",Id)

        val EF = ExploreFragment()
        EF.arguments = bundle


        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        binding = CouponmessoFragmentBinding.inflate(inflater)

        binding.coupontext3.text = resources.getString(R.string.inserted_coupon_fragment)

        binding.inseriscicoupon2.setOnClickListener(){

            transaction.addToBackStack(null)
            transaction.replace(R.id.fragmentContainerView2, EF)
            transaction.commit()
        }
        return binding.root

    }
}