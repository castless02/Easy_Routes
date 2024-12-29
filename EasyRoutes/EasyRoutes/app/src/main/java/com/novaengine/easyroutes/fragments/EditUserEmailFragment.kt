package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.InfoInterface
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.EmailFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService

class EditUserEmailFragment : Fragment(R.layout.activity_account) {

    private lateinit var binding: EmailFragmentBinding
    private lateinit var fm: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        binding = EmailFragmentBinding.inflate(inflater)
        var check:String = "null"
        var Id = arguments?.getString("Id").toString()

        var bundle = Bundle()
        bundle.putString("Id",Id)

        val EMF = EmailModificataFragment()
        EMF.arguments = bundle

        val MPF = MainProfileFragment()
        MPF.arguments = bundle

        binding.indietro.setOnClickListener(){
            transaction.replace(R.id.fragmentContainerView, MPF)
            transaction.commit()
        }




        binding.entra.setOnClickListener(){

            if(binding.email.text.isNotEmpty()){
                binding.icon5.visibility = View.INVISIBLE
                if(binding.confirmemail.text.isNotEmpty()){


                    if(binding.email.text.toString() == binding.confirmemail.text.toString()){

                        DBQueriesService( ).changeEmail(Id,binding.email.text.toString(), object:
                            InfoInterface {
                            override fun onReturnValue(response: Any) {

                                binding.errore.visibility = View.INVISIBLE
                                binding.icon6.visibility = View.INVISIBLE
                                binding.icon5.visibility = View.INVISIBLE

                                transaction.replace(R.id.fragmentContainerView, EMF)
                                transaction.commit()
                            }
                            override fun onError(response: Any) {
                                binding.errore.visibility = View.VISIBLE

                            }
                        } )


                    }else{

                        binding.errore.visibility = View.VISIBLE
                        binding.icon6.visibility = View.INVISIBLE
                        binding.icon5.visibility = View.INVISIBLE
                    }

                }else{
                    binding.icon6.visibility = View.VISIBLE
                    binding.errore.visibility = View.VISIBLE
                }
            }else{
                binding.icon5.visibility = View.VISIBLE
                binding.errore.visibility = View.VISIBLE
                if(binding.confirmemail.text.isEmpty()){
                    binding.icon6.visibility = View.VISIBLE
                }else{
                    binding.icon6.visibility = View.INVISIBLE
                }

            }


        }
        return binding.root

    }
}