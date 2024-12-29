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
import com.novaengine.easyroutes.databinding.MenuospiteFragmentBinding


class MenuOspiteFragment : Fragment(R.layout.activity_explore) {

    private lateinit var binding: MenuospiteFragmentBinding
    private lateinit var fm:FragmentManager
    lateinit var text:String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()


        binding = MenuospiteFragmentBinding.inflate(inflater)
        binding.back.setOnClickListener {
            val i = Intent(context, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }



        return binding.root


    }
}