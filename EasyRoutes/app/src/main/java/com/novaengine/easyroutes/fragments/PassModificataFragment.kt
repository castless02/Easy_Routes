package com.novaengine.easyroutes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.MainActivity
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.PassmodificataFragmentBinding


class PassModificataFragment : Fragment(R.layout.activity_explore) {

    private lateinit var binding: PassmodificataFragmentBinding
    private lateinit var fm:FragmentManager
    lateinit var text:String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        binding = PassmodificataFragmentBinding.inflate(inflater)

        var Id = arguments?.getString("Id").toString()

        var bundle = Bundle()
        bundle.putString("Id",Id)

        val MPF = MainProfileFragment()
        MPF.arguments = bundle

        binding.ok2.setOnClickListener {
            val i = Intent(context, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }



        return binding.root


    }
}