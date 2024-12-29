package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.ModificapasswordFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService

class EditPasswordFragment : Fragment(R.layout.modificapassword_fragment) {

    private lateinit var binding: ModificapasswordFragmentBinding
    private lateinit var fm: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        binding = ModificapasswordFragmentBinding.inflate(inflater)

        var Id = arguments?.getString("Id").toString()

        var bundle = Bundle()
        bundle.putString("Id",Id)

        val MPF = MainProfileFragment()
        MPF.arguments = bundle

        val PMF = PassModificataFragment()
        PMF.arguments = bundle





        binding.indietro.setOnClickListener(){
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }
        binding.entra.setOnClickListener(){


            if(binding.newpassword.text.isNotEmpty()){
                binding.icon5.visibility = View.INVISIBLE
                if(binding.oldpassword.text.isNotEmpty()){

                    if(binding.newpassword.text.toString() == binding.oldpassword.text.toString()){

                       //DbmsQuery().changePassword(binding.newpassword.text.toString(), Id)

                        DBQueriesService( ).changePassword(Id,binding.newpassword.text.toString())

                        binding.errore.visibility = View.INVISIBLE
                        binding.icon6.visibility = View.INVISIBLE
                        binding.icon5.visibility = View.INVISIBLE

                        transaction.replace(R.id.fragmentContainerView, PMF)
                        transaction.commit()

                    }else{

                        binding.errore.visibility = VISIBLE
                        binding.icon6.visibility = View.INVISIBLE
                        binding.icon5.visibility = View.INVISIBLE
                    }
                }else{
                    binding.icon6.visibility = VISIBLE
                    binding.errore.visibility = VISIBLE
                }
            }else{
                binding.icon5.visibility = VISIBLE
                binding.errore.visibility = VISIBLE
                if(binding.oldpassword.text.isEmpty()){
                    binding.icon6.visibility = VISIBLE
                }else{
                    binding.icon6.visibility = View.INVISIBLE
                }

            }


        }



        return binding.root

    }
}