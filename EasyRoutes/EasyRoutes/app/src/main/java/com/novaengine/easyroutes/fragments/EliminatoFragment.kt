package com.novaengine.easyroutes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.ExploreActivity
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.EliminatoFragmentBinding

class EliminatoFragment : Fragment(R.layout.eliminato_fragment) {

    private lateinit var binding: EliminatoFragmentBinding
    private lateinit var fm: FragmentManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var Id = arguments?.getString("Id").toString()
        var Luogo = arguments?.getString("Luogo").toString()
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        binding = EliminatoFragmentBinding.inflate(inflater)
        binding.fatto.setOnClickListener(){

            val i = Intent(context, ExploreActivity::class.java)
            i.putExtra("Loggato", "vero")
            i.putExtra("Id", Id)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)

        }
        return binding.root

    }
}