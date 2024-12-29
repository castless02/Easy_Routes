package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.EditIconFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService

class EditIconFragment : Fragment(R.layout.edit_icon_fragment) {

    private lateinit var binding: EditIconFragmentBinding
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

        binding = EditIconFragmentBinding.inflate(inflater)


        binding.icona1.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon1.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.icona2.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon2.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.icona3.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon3.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.icona4.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon4.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.icona5.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon5.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.icona6.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon6.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.icona7.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon7.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.icona8.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon8.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.icona9.setOnClickListener() {
            DBQueriesService( ).changeAvatar(Id,"media/images/icone/icon9.png")
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }

        binding.indietroicone.setOnClickListener() {
            // transaction.addToBackStack(null)
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()

        }



        return binding.root

    }
}